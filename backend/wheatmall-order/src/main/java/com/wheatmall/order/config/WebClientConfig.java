package com.wheatmall.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * WebClient配置类
 */
@Configuration
public class WebClientConfig {

    /**
     * 配置WebClient Bean
     * @return WebClient实例
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

    /**
     * 配置Product服务的WebClient
     * @param builder WebClient.Builder
     * @return 针对Product服务的WebClient
     */
    @Bean
    public WebClient productWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081")
                .build();
    }
}
