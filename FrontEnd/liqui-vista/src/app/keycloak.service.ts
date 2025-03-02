import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private keycloak: Keycloak;
  private isAuthenticated = false;
  private token: string | null = null;

  constructor(private http: HttpClient) {
    this.keycloak = new Keycloak({
      url: 'http://localhost:9090',
      realm: 'liqui-vista',
      clientId: 'bsn'
    });
  }

  /**
   * Initializes Keycloak and checks if the user is already logged in.
   */
  async init(): Promise<boolean> {
    try {
      await this.keycloak.init({
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html'
      });

      this.isAuthenticated = this.keycloak.authenticated ?? false;

      if (this.isAuthenticated) {
        console.log('User is authenticated via SSO');
        this.token = this.keycloak.token || '';
        localStorage.setItem('token', this.token);
      } else {
        console.log('User is not authenticated');
      }

      return this.isAuthenticated;
    } catch (error) {
      console.error('Keycloak initialization failed', error);
      return false;
    }
  }

  /**
   * Logs in using Keycloak's built-in login page.
   */
  loginWithSSO() {
    this.keycloak.login();
  }

  /**
   * Logs in manually using username & password (direct API call to Keycloak token endpoint).
   */
  async login(username: string, password: string): Promise<boolean> {
    const body = new URLSearchParams();
    body.set('client_id', 'bsn');
    body.set('grant_type', 'password');
    body.set('username', username);
    body.set('password', password);

    try {
      const response: any = await firstValueFrom(
        this.http.post(
          'http://localhost:9090/realms/liqui-vista/protocol/openid-connect/token',
          body.toString(),
          { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
        )
      );
      this.token = response.access_token;
      localStorage.setItem('token', this.token ?? '');
      console.log('User logged in via direct API call');
      return true;
    } catch (error) {
      console.error('Login failed', error);
      return false;
    }
  }

  /**
   * Logs out from Keycloak (removes session & token).
   */
  logout() {
    this.token = null;
    localStorage.removeItem('token');
    this.keycloak.logout();
  }

  /**
   * Returns the current token.
   */
  getToken(): string | null {
    return this.token || localStorage.getItem('token');
  }
}
