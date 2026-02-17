package com.wheatmall.authadmin.service;

import com.wheatmall.authadmin.dto.LoginRequest;
import com.wheatmall.authadmin.security.jwt.JwtUtil;
import com.wheatmall.authadmin.vo.LoginResponse;
import com.wheatmall.authadmin.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 认证服务实现（简单实现，供测试使用）
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Object principal = authentication.getPrincipal();
        Long userId = 1L;
        String username = request.getUsername();

        String accessToken = jwtUtil.generateAccessToken(userId, username, Arrays.asList("ADMIN", "USER"));
        String refreshToken = jwtUtil.generateRefreshToken(userId);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(1800L)
                .tokenType("Bearer")
                .userInfo(UserInfoVO.builder()
                        .userId(userId)
                        .username(username)
                        .email(username + "@wheatmall.com")
                        .roles(Arrays.asList("ADMIN", "USER"))
                        .build())
                .build();
    }

    @Override
    public void logout(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token不能为空");
        }
        jwtUtil.getUserIdFromToken(token);
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Token不能为空");
        }

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new com.wheatmall.authadmin.exception.TokenInvalidException("Token无效");
        }

        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        String username = "admin";

        String newAccessToken = jwtUtil.generateAccessToken(userId, username, Arrays.asList("ADMIN", "USER"));
        String newRefreshToken = jwtUtil.generateRefreshToken(userId);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(1800L)
                .tokenType("Bearer")
                .userInfo(UserInfoVO.builder()
                        .userId(userId)
                        .username(username)
                        .email(username + "@wheatmall.com")
                        .roles(Arrays.asList("ADMIN", "USER"))
                        .build())
                .build();
    }

    @Override
    public UserInfoVO getCurrentUser() {
        return UserInfoVO.builder()
                .userId(1L)
                .username("admin")
                .email("admin@wheatmall.com")
                .roles(Arrays.asList("ADMIN", "USER"))
                .build();
    }
}
