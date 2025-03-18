import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { KeycloakService } from '../keycloak.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule,HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
  
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private keycloakService: KeycloakService,private http: HttpClient) {}

  loginWithSSO() {
    this.keycloakService.loginWithSSO();
  }

  async product(){
    this.http.get('http://localhost:9222/product-management/get-all-products').subscribe(
      data => console.log('Data from API Gateway:', data),
      error => console.error('Error:', error)
    );
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
