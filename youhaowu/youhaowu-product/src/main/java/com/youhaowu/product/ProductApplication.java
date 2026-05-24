package com.youhaowu.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.youhaowu.product.mapper")
public class ProductApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ProductApplication.class, args);
    }
}
