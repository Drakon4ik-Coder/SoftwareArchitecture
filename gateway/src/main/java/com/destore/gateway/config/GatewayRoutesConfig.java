package com.destore.gateway.config;

import org.springframework.beans.factory.annotation.Value;
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
    RouteLocator routes(RouteLocatorBuilder builder,
                        @Value("${destore.routes.pricing.host:localhost}") String pricingHost,
                        @Value("${destore.routes.pricing.port:8081}") int pricingPort,
                        @Value("${destore.routes.inventory.host:localhost}") String inventoryHost,
                        @Value("${destore.routes.inventory.port:8082}") int inventoryPort,
                        @Value("${destore.routes.finance.host:localhost}") String financeHost,
                        @Value("${destore.routes.finance.port:8083}") int financePort,
                        @Value("${destore.routes.notification.host:localhost}") String notificationHost,
                        @Value("${destore.routes.notification.port:8084}") int notificationPort) {
        return builder.routes()
                .route("pricing", r -> r.path("/pricing/**").uri("http://" + pricingHost + ":" + pricingPort))
                .route("inventory", r -> r.path("/inventory/**").uri("http://" + inventoryHost + ":" + inventoryPort))
                .route("finance", r -> r.path("/finance/**").uri("http://" + financeHost + ":" + financePort))
                .route("notification", r -> r.path("/notify/**").uri("http://" + notificationHost + ":" + notificationPort))
                .build();
    }
}
