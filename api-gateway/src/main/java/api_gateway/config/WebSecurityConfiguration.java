package api_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
//
//    private static final String JWK_SET_URI = "http://localhost:8080/realms/keycloak-app/protocol/openid-connect/certs";
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/public/**").permitAll()
//                        .pathMatchers("/eureka/**").permitAll()
//                        .pathMatchers("/user-management/**").authenticated()
//                        .pathMatchers("/product-management/**").authenticated()
//                        .pathMatchers("/dms/**").authenticated()
//                        .pathMatchers("/notification-management/**").authenticated()
//                        .anyExchange().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
//        return serverHttpSecurity.build();
//    }
//

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);

        // Make sure to set the max age to avoid excessive preflight requests
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}