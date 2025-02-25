package api_gateway.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import java.net.URI;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> documentServiceRoute() {
        return RouterFunctions.route(RequestPredicates.path("/dms/**"),
                request -> ServerResponse
                        .temporaryRedirect(URI.create("http://localhost:8080" + request.uri().getPath()))
                        .build());
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return RouterFunctions.route(RequestPredicates.path("/product-management/**"),
                request -> ServerResponse
                        .temporaryRedirect(URI.create("http://localhost:8081" + request.uri().getPath()))
                        .build());
    }

    @Bean
    public RouterFunction<ServerResponse> userManagementServiceRoute() {
        return RouterFunctions.route(RequestPredicates.path("/user-management/**"),
                request -> ServerResponse
                        .temporaryRedirect(URI.create("http://localhost:8888" + request.uri().getPath()))
                        .build());
    }
}
