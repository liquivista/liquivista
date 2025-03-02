import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { KeycloakService } from '../keycloak.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
  
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private keycloakService: KeycloakService) {}

  loginWithSSO() {
    this.keycloakService.loginWithSSO();
  }

  async loginWithCredentials() {
    const success = await this.keycloakService.login(this.username, this.password);
    if (success) {
      console.log('Login successful');
    } else {
      console.log('Login failed');
    }
  }
}
