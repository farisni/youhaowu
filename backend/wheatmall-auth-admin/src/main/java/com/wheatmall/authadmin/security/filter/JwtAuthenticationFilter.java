package com.wheatmall.authadmin.security.filter;

import com.wheatmall.authadmin.exception.TokenInvalidException;
import com.wheatmall.authadmin.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 认证过滤器
 * 
 * 功能：
 * 1. 拦截所有请求
 * 2. 提取 Bearer Token
 * 3. 验证 Token 有效性
 * 4. 设置 SecurityContext
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. 从请求头获取 Authorization: Bearer <token>
            String authHeader = request.getHeader("Authorization");
            
            // 2. 检查是否为 Bearer Token
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                // 3. 验证 Token 有效性
                if (jwtUtil.validateToken(token)) {
                    // 4. 解析 Token 获取用户信息
                    String username = jwtUtil.getUsernameFromToken(token);
                    
                    // 5. 加载用户权限
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    // 6. 创建 Authentication 对象
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    
                    // 7. 设置 SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    log.debug("JWT Token 验证成功，用户: {}", username);
                } else {
                    log.warn("JWT Token 无效: {}", token);
                }
            }
        } catch (Exception e) {
            log.error("JWT 过滤器处理异常", e);
            // 继续过滤链，让后续的异常处理器处理
        }
        
        filterChain.doFilter(request, response);
    }
}
