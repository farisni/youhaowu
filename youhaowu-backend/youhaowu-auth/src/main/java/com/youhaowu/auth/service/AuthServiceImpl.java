package com.youhaowu.auth.service;

import com.youhaowu.auth.dto.LoginRequest;
import com.youhaowu.auth.entity.SysUser;
import com.youhaowu.auth.mock.MockData;
import com.youhaowu.auth.security.jwt.JwtUtil;
import com.youhaowu.auth.security.service.SecurityUser;
import com.youhaowu.auth.vo.LoginResponse;
import com.youhaowu.auth.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        Long userId = securityUser.getUser().getId();
        String username = securityUser.getUsername();

        String accessToken = jwtUtil.generateAccessToken(userId, username, securityUser.getRoles());
        String refreshToken = jwtUtil.generateRefreshToken(userId);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(1800L)
                .tokenType("Bearer")
                .userInfo(UserInfoVO.builder()
                        .userId(userId)
                        .username(username)
                        .email(securityUser.getUser().getEmail())
                        .roles(securityUser.getRoles())
                        .permissions(securityUser.getPermissions())
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
            throw new com.youhaowu.auth.exception.TokenInvalidException("Token无效");
        }

        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        // 从MockData获取用户信息
        SysUser user = MockData.getUserById(userId);
        if (user == null) {
            throw new com.youhaowu.auth.exception.TokenInvalidException("用户不存在");
        }
        
        List<String> roles = MockData.getRoleCodesByUserId(userId);
        List<String> permissions = MockData.getPermissionCodesByUserId(userId);

        String newAccessToken = jwtUtil.generateAccessToken(userId, user.getUsername(), roles);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(1800L)
                .tokenType("Bearer")
                .userInfo(UserInfoVO.builder()
                        .userId(userId)
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(roles)
                        .permissions(permissions)
                        .build())
                .build();
    }

    @Override
    public UserInfoVO getCurrentUser() {
        // 这里应该从 SecurityContext 获取当前用户
        // 为了测试，返回一个默认用户
        SysUser user = MockData.getUserByUsername("admin");
        if (user != null) {
            List<String> roles = MockData.getRoleCodesByUserId(user.getId());
            List<String> permissions = MockData.getPermissionCodesByUserId(user.getId());
            
            return UserInfoVO.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(roles)
                    .permissions(permissions)
                    .build();
        }
        
        return UserInfoVO.builder()
                .userId(1L)
                .username("admin")
                .email("admin@wheatmall.com")
                .roles(List.of("ADMIN", "USER"))
                .build();
    }
}