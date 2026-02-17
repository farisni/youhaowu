package com.wheatmall.authadmin.service;

import com.wheatmall.authadmin.security.jwt.JwtUtil;
import com.wheatmall.authadmin.vo.LoginResponse;
import com.wheatmall.authadmin.dto.LoginRequest;
import com.wheatmall.authadmin.dto.RefreshTokenRequest;
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
 * 
 * testLoginSuccess 方法详解：
 * 1. Given: 准备测试数据 - 创建 LoginRequest 对象（用户名"admin"，密码"123456"）
 * 2. When: Mock依赖行为 - 模拟 AuthenticationManager.authenticate() 返回认证对象，
 *          模拟 JwtUtil.generateAccessToken() 返回 "mock-access-token"，
 *          模拟 JwtUtil.generateRefreshToken() 返回 "mock-refresh-token"
 * 3. When: 执行测试 - 调用 authService.login(request) 执行登录操作
 * 4. Then: 验证结果 - 验证返回的 LoginResponse 内容正确（token值、过期时间、token类型），
 *          验证 Mock 对象的方法被正确调用
 * 
 * 核心目的：测试在正常情况下，AuthService 的 login 方法能否正确协调 
 * AuthenticationManager 和 JwtUtil 来完成用户认证和 Token 生成，
 * 确保返回的响应格式和内容符合预期。
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

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(createMockSecurityUser());
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
        assertEquals(1800L, response.getExpiresIn());
        assertEquals("Bearer", response.getTokenType());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateAccessToken(anyLong(), eq(TEST_USERNAME), eq(TEST_ROLES));
        verify(jwtUtil, times(1)).generateRefreshToken(eq(TEST_USER_ID));
    }

    @Test
    @DisplayName("测试登录 - 用户名为空")
    void testLoginWithEmptyUsername() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword(TEST_PASSWORD);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> authService.login(request));

        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).generateAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("测试登录 - 密码为空")
    void testLoginWithEmptyPassword() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername(TEST_USERNAME);
        request.setPassword("");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> authService.login(request));

        verify(authenticationManager, never()).authenticate(any());
        verify(jwtUtil, never()).generateAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("测试登出成功")
    void testLogoutSuccess() {
        // Given
        String token = "valid-token";
        when(jwtUtil.getUserIdFromToken(token)).thenReturn(TEST_USER_ID);

        // When
        authService.logout(token);

        // Then
        verify(jwtUtil, times(1)).getUserIdFromToken(token);
    }

    @Test
    @DisplayName("测试登出 - Token为空")
    void testLogoutWithEmptyToken() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> authService.logout(""));
        assertThrows(IllegalArgumentException.class, () -> authService.logout(null));

        verify(jwtUtil, never()).getUserIdFromToken(any());
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

        verify(jwtUtil, times(1)).validateToken(refreshToken);
        verify(jwtUtil, times(1)).getUserIdFromToken(refreshToken);
        verify(jwtUtil, times(1)).generateAccessToken(anyLong(), anyString(), anyList());
        verify(jwtUtil, times(1)).generateRefreshToken(anyLong());
    }

    @Test
    @DisplayName("测试刷新 Token - Token无效")
    void testRefreshTokenInvalid() {
        // Given
        String refreshToken = "invalid-token";
        when(jwtUtil.validateToken(refreshToken)).thenReturn(false);

        // When & Then
        assertThrows(com.wheatmall.authadmin.exception.TokenInvalidException.class,
                () -> authService.refreshToken(refreshToken));

        verify(jwtUtil, times(1)).validateToken(refreshToken);
        verify(jwtUtil, never()).generateAccessToken(any(), any(), any());
    }

    @Test
    @DisplayName("测试刷新 Token - Token为空")
    void testRefreshTokenWithEmptyToken() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> authService.refreshToken(""));
        assertThrows(IllegalArgumentException.class, () -> authService.refreshToken(null));

        verify(jwtUtil, never()).validateToken(any());
    }

    @Test
    @DisplayName("测试获取当前用户信息成功")
    void testGetCurrentUserSuccess() {
        // When
        UserInfoVO userInfo = authService.getCurrentUser();

        // Then
        assertNotNull(userInfo);
        assertEquals(TEST_USER_ID, userInfo.getUserId());
        assertEquals(TEST_USERNAME, userInfo.getUsername());
        assertEquals(TEST_ROLES, userInfo.getRoles());
    }

    @Test
    @DisplayName("测试登录 - 认证失败")
    void testLoginAuthenticationFailed() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername(TEST_USERNAME);
        request.setPassword("wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Bad credentials"));

        // When & Then
        assertThrows(org.springframework.security.authentication.BadCredentialsException.class,
                () -> authService.login(request));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateAccessToken(any(), any(), any());
    }

    /**
     * 创建 Mock SecurityUser 对象
     */
    private Object createMockSecurityUser() {
        // 由于实际 SecurityUser 类还未创建，这里返回一个 Mock 对象
        // 实际实现时应该返回真实的 SecurityUser 实例
        return new Object() {
            public Long getId() {
                return TEST_USER_ID;
            }

            public String getUsername() {
                return TEST_USERNAME;
            }

            public List<String> getRoles() {
                return TEST_ROLES;
            }
        };
    }
}
