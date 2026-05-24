package com.youhaowu.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway 路由配置（Java Config 方式，避免 spring.cloud.gateway.routes YAML 弃用）
 */
@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product_route", r -> r.path("/api/product/**")
                        .uri("lb://youhaowu-product"))
                .route("order_route", r -> r.path("/api/order/**")
                        .uri("lb://youhaowu-order"))
                .route("ware_route", r -> r.path("/api/ware/**")
                        .uri("lb://youhaowu-ware"))
                .route("coupon_route", r -> r.path("/api/coupon/**")
                        .uri("lb://youhaowu-coupon"))
                .route("cart_route", r -> r.path("/api/cart/**")
                        .uri("lb://youhaowu-cart"))
                .route("member_route", r -> r.path("/api/member/**")
                        .uri("lb://youhaowu-member"))
                .route("thirdparty_route", r -> r.path("/api/thirdparty/**")
                        .uri("lb://youhaowu-thirdparty"))
                .route("search_route", r -> r.path("/api/search/**")
                        .uri("lb://youhaowu-search"))
                .route("auth_route", r -> r.path("/api/user/**")
                        .uri("lb://youhaowu-auth"))
                .build();
    }
}
