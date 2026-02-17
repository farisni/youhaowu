package com.wheatmall.authadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证授权管理模块启动类
 * 
 * 模块职责：
 * 1. 用户认证（登录/登出）
 * 2. JWT Token签发与验证
 * 3. RBAC权限管理
 * 4. Token黑名单管理
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthAdminApplication.class, args);
    }
}
