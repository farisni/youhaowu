package com.youhaowu.common.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 自动装配引导 — 只扫 config 和 advice，避免注入 Kafka 等非公共依赖
 */
@AutoConfiguration
@ComponentScan(basePackages = {
    "com.youhaowu.common.config",
    "com.youhaowu.common.advice"
})
public class CommonAutoConfiguration {
}
