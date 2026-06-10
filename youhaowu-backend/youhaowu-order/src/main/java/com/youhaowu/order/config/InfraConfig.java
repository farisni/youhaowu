package com.youhaowu.order.config;

import com.youhaowu.common.interceptor.ApiLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Order 模块基础设施配置，按需引入 Kafka + API 日志拦截器。
 */
@Configuration
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
