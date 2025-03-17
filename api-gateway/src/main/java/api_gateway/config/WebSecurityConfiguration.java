package api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;



@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    private static final String JWK_SET_URI = "http://localhost:8080/realms/keycloak-app/protocol/openid-connect/certs";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/public/**").permitAll()
                        .pathMatchers("/eureka/**").permitAll()
                        .pathMatchers("/user-management/**").authenticated()
                        .pathMatchers("/product-management/**").authenticated()
                        .pathMatchers("/dms/**").authenticated()
                        .pathMatchers("/notification-management/**").authenticated()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return serverHttpSecurity.build();
    }

}