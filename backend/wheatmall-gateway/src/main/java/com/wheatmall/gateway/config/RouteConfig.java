package com.wheatmall.gateway.config;

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
                        .uri("lb://wheatmall-product"))
                .route("order_route", r -> r.path("/api/order/**")
                        .uri("lb://wheatmall-order"))
                .route("ware_route", r -> r.path("/api/ware/**")
                        .uri("lb://wheatmall-ware"))
                .route("coupon_route", r -> r.path("/api/coupon/**")
                        .uri("lb://wheatmall-coupon"))
                .route("member_route", r -> r.path("/api/member/**")
                        .uri("lb://wheatmall-member"))
                .route("thirdparty_route", r -> r.path("/api/thirdparty/**")
                        .uri("lb://wheatmall-thirdparty"))
                .route("search_route", r -> r.path("/api/search/**")
                        .uri("lb://wheatmall-search"))
                .route("auth_route", r -> r.path("/api/user/**")
                        .uri("lb://wheatmall-auth"))
                .build();
    }
}
