package com.wheatmall.authadmin.service;

import com.wheatmall.authadmin.dto.LoginRequest;
import com.wheatmall.authadmin.entity.SysUser;
import com.wheatmall.authadmin.security.jwt.JwtUtil;
import com.wheatmall.authadmin.security.service.SecurityUser;
import com.wheatmall.authadmin.vo.LoginResponse;
import com.wheatmall.authadmin.vo.UserInfoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AuthService 测试类
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    private static final Long TEST_USER_ID = 1L;
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "123456";
    private static final List<String> TEST_ROLES = Arrays.asList("ADMIN", "USER");

    @BeforeEach
    void setUp() {
        // 重置 Mock
        reset(authenticationManager, jwtUtil, authentication);
    }

    @Test
    @DisplayName("测试登录成功")
    void testLoginSuccess() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername(TEST_USERNAME);
        request.setPassword(TEST_PASSWORD);

        // 创建模拟的 SecurityUser
        SysUser mockUser = new SysUser();
        mockUser.setId(TEST_USER_ID);
        mockUser.setUsername(TEST_USERNAME);
        mockUser.setEmail(TEST_USERNAME + "@wheatmall.com");
        mockUser.setPassword("encoded-password");
        
        SecurityUser securityUser = new SecurityUser(mockUser, TEST_ROLES, Arrays.asList("user:view"));
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(jwtUtil.generateAccessToken(any(Long.class), anyString(), anyList()))
                .thenReturn("mock-access-token");
        when(jwtUtil.generateRefreshToken(anyLong()))
                .thenReturn("mock-refresh-token");

        // When
        LoginResponse response = authService.login(request);

        // Then
        assertNotNull(response);
        assertEquals("mock-access-token", response.getAccessToken());
        assertEquals("mock-refresh-token", response.getRefreshToken());
    }

    @Test
    @DisplayName("测试登出成功")
    void testLogoutSuccess() {
        // Given
        String token = "valid-token";
        when(jwtUtil.getUserIdFromToken(token)).thenReturn(TEST_USER_ID);

        // When & Then
        assertDoesNotThrow(() -> authService.logout(token));
        verify(jwtUtil, times(1)).getUserIdFromToken(token);
    }

    @Test
    @DisplayName("测试刷新 Token 成功")
    void testRefreshTokenSuccess() {
        // Given
        String refreshToken = "valid-refresh-token";
        when(jwtUtil.validateToken(refreshToken)).thenReturn(true);
        when(jwtUtil.getUserIdFromToken(refreshToken)).thenReturn(TEST_USER_ID);
        when(jwtUtil.generateAccessToken(anyLong(), anyString(), anyList()))
                .thenReturn("new-access-token");
        when(jwtUtil.generateRefreshToken(anyLong()))
                .thenReturn("new-refresh-token");

        // When
        LoginResponse response = authService.refreshToken(refreshToken);

        // Then
        assertNotNull(response);
        assertEquals("new-access-token", response.getAccessToken());
        assertEquals("new-refresh-token", response.getRefreshToken());
    }

    @Test
    @DisplayName("测试获取当前用户信息成功")
    void testGetCurrentUserSuccess() {
        // When
        UserInfoVO userInfo = authService.getCurrentUser();

        // Then
        assertNotNull(userInfo);
        assertEquals("admin", userInfo.getUsername());
    }
}