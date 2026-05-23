package com.wheatmall.product.config;

import com.wheatmall.common.config.KafkaConfig;
import com.wheatmall.common.interceptor.ApiLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 产品模块基础设施配置，按需引入 Kafka + API 日志拦截器。
 */
@Configuration
@Import(KafkaConfig.class)
public class InfraConfig implements WebMvcConfigurer {

    @Bean
    public ApiLogInterceptor apiLogInterceptor() {
        return new ApiLogInterceptor();
    }

    @Autowired
    private ApiLogInterceptor apiLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLogInterceptor)
                .addPathPatterns("/**");
    }
}
