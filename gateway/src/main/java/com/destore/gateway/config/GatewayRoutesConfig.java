package com.destore.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Simple route mapping so the gateway acts as the single entry point.
 * Update target URIs when services run on actual ports.
 */
@Configuration
public class GatewayRoutesConfig {

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("pricing", r -> r.path("/pricing/**").uri("http://localhost:8081"))
                .route("inventory", r -> r.path("/inventory/**").uri("http://localhost:8082"))
                .route("finance", r -> r.path("/finance/**").uri("http://localhost:8083"))
                .route("notification", r -> r.path("/notify/**").uri("http://localhost:8084"))
                .build();
    }
}
