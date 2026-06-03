package com.youhaowu.product.config;

import com.youhaowu.common.remote.WareClient;
import com.youhaowu.product.remote.CouponClient;
import com.youhaowu.product.remote.SearchClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Product 模块远程客户端注册（WebClient + @HttpExchange 声明式）
 */
@Configuration
public class RemoteClientConfig {

    @Value("${app.service.ware}")
    private String wareUrl;

    @Value("${app.service.coupon}")
    private String couponUrl;

    @Value("${app.service.search}")
    private String searchUrl;

    @Bean
    public WareClient wareClient(WebClient.Builder builder) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(builder.baseUrl(wareUrl).build()))
                .build()
                .createClient(WareClient.class);
    }

    @Bean
    public CouponClient couponClient(WebClient.Builder builder) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(builder.baseUrl(couponUrl).build()))
                .build()
                .createClient(CouponClient.class);
    }

    @Bean
    public SearchClient searchClient(WebClient.Builder builder) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(builder.baseUrl(searchUrl).build()))
                .build()
                .createClient(SearchClient.class);
    }
}
