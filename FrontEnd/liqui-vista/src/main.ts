import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { KeycloakService } from './app/keycloak.service';
import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';
import { APP_INITIALIZER } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { HttpTokenInterceptor } from './app/http-token.interceptor';

function keycloakInitializer(keycloakService: KeycloakService) {
  return () => keycloakService.init();
}

bootstrapApplication(AppComponent, {
  providers: [
    KeycloakService,
    provideHttpClient(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: keycloakInitializer,
      deps: [KeycloakService],
      multi: true
    },
    provideRouter(routes)
  ]
}).catch((err) => console.error(err));
