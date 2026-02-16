package com.wheatmall.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient配置类
 * 支持Nacos服务发现和负载均衡
 */
@Configuration
public class WebClientConfig {

    /**
     * 配置支持负载均衡的WebClient.Builder
     * 使用@LoadBalanced注解启用服务名调用
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    /**
     * 配置Product服务的WebClient
     * 使用服务名 wheatmall-product 通过Nacos发现和调用
     */
    @Bean
    public WebClient productWebClient(WebClient.Builder loadBalancedWebClientBuilder) {
        return loadBalancedWebClientBuilder
                .baseUrl("http://wheatmall-product")
                .build();
    }
}
