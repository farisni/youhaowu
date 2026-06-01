package com.youhaowu.product.config;

import com.youhaowu.common.remote.WareClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Product 模块远程客户端注册
 */
@Configuration
public class RemoteClientConfig {

    @Value("${app.service.ware}")
    private String wareUrl;

    @Bean
    public WareClient wareClient(RestClient.Builder builder) {
        RestClient client = builder.baseUrl(wareUrl).build();
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(client))
                .build()
                .createClient(WareClient.class);
    }
}
