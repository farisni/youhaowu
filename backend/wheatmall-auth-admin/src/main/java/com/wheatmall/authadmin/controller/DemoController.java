package com.wheatmall.authadmin.controller;

import com.wheatmall.common.utils.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限演示控制器
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    
    @GetMapping("/public")
    public R<String> publicApi() {
        return R.ok("这是一个公开接口，无需认证");
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public R<String> userApi() {
        return R.ok("需要 USER 角色");
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public R<String> adminApi() {
        return R.ok("需要 ADMIN 角色");
    }
    
    @GetMapping("/user/create")
    @PreAuthorize("hasAuthority('user:create')")
    public R<String> createUser() {
        return R.ok("需要 user:create 权限");
    }
    
    @GetMapping("/user/view")
    @PreAuthorize("hasAnyAuthority('user:view', 'user:admin')")
    public R<String> viewUser() {
        return R.ok("需要 user:view 或 user:admin 权限");
    }
}
