package com.youhaowu.product.config;

import com.youhaowu.common.config.KafkaConfig;
import com.youhaowu.common.interceptor.ApiLogInterceptor;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLogInterceptor())
                .addPathPatterns("/**");
    }
}
