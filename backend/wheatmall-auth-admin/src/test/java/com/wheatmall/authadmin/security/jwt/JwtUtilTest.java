package com.wheatmall.authadmin.security.jwt;

import com.wheatmall.authadmin.exception.TokenInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtUtil 测试类
 */
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final Long TEST_USER_ID = 1L;
    private static final String TEST_USERNAME = "admin";
    private static final List<String> TEST_ROLES = Arrays.asList("ADMIN", "USER");

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.setSecret("test-secret-test-secret-test-secret-key");
        jwtUtil.setAccessTokenExpiration(1800L);  // 30分钟
        jwtUtil.setRefreshTokenExpiration(604800L);  // 7天
    }

    @Test
    @DisplayName("测试生成 Access Token")
    void testGenerateAccessToken() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    @DisplayName("测试生成 Refresh Token")
    void testGenerateRefreshToken() {
        String token = jwtUtil.generateRefreshToken(TEST_USER_ID);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    @DisplayName("测试从 Access Token 提取用户ID")
    void testGetUserIdFromAccessToken() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        Long userId = jwtUtil.getUserIdFromToken(token);
        assertEquals(TEST_USER_ID, userId);
    }

    @Test
    @DisplayName("测试从 Refresh Token 提取用户ID")
    void testGetUserIdFromRefreshToken() {
        String token = jwtUtil.generateRefreshToken(TEST_USER_ID);
        Long userId = jwtUtil.getUserIdFromToken(token);
        assertEquals(TEST_USER_ID, userId);
    }

    @Test
    @DisplayName("测试从 Token 提取用户名")
    void testGetUsernameFromToken() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals(TEST_USERNAME, username);
    }

    @Test
    @DisplayName("测试从 Token 提取角色列表")
    void testGetRolesFromToken() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        List<String> roles = jwtUtil.getRolesFromToken(token);
        assertEquals(TEST_ROLES, roles);
    }

    @Test
    @DisplayName("测试验证有效 Access Token")
    void testValidateValidAccessToken() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    @DisplayName("测试验证有效 Refresh Token")
    void testValidateValidRefreshToken() {
        String token = jwtUtil.generateRefreshToken(TEST_USER_ID);
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    @DisplayName("测试验证过期 Token - 应返回 false")
    void testValidateExpiredToken() {
        jwtUtil.setAccessTokenExpiration(-1L);
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        assertFalse(jwtUtil.validateToken(token));
    }

    @Test
    @DisplayName("测试过期 Token 抛出异常")
    void testExpiredTokenThrowException() {
        jwtUtil.setAccessTokenExpiration(-1L);
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        assertThrows(TokenInvalidException.class, () -> jwtUtil.parseToken(token));
    }

    @Test
    @DisplayName("测试验证无效 Token - 格式错误")
    void testValidateInvalidTokenFormat() {
        String invalidToken = "invalid.token.format";
        assertFalse(jwtUtil.validateToken(invalidToken));
    }

    @Test
    @DisplayName("测试验证空 Token")
    void testValidateEmptyToken() {
        assertFalse(jwtUtil.validateToken(""));
        assertFalse(jwtUtil.validateToken(null));
    }

    @Test
    @DisplayName("测试验证签名错误的 Token")
    void testValidateWrongSignatureToken() {
        JwtUtil wrongJwtUtil = new JwtUtil();
        wrongJwtUtil.setSecret("wrong-secret-wrong-secret-wrong-secret");
        wrongJwtUtil.setAccessTokenExpiration(1800L);

        String wrongToken = wrongJwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        assertFalse(jwtUtil.validateToken(wrongToken));
    }

    @Test
    @DisplayName("测试判断 Access Token 类型")
    void testIsAccessToken() {
        String accessToken = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        String refreshToken = jwtUtil.generateRefreshToken(TEST_USER_ID);
        assertTrue(jwtUtil.isAccessToken(accessToken));
        assertFalse(jwtUtil.isAccessToken(refreshToken));
    }

    @Test
    @DisplayName("测试判断 Refresh Token 类型")
    void testIsRefreshToken() {
        String accessToken = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        String refreshToken = jwtUtil.generateRefreshToken(TEST_USER_ID);
        assertTrue(jwtUtil.isRefreshToken(refreshToken));
        assertFalse(jwtUtil.isRefreshToken(accessToken));
    }

    @Test
    @DisplayName("测试获取 Token 过期时间")
    void testGetExpirationDate() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        Date expirationDate = jwtUtil.getExpirationDate(token);
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    @DisplayName("测试获取 Token 剩余有效时间")
    void testGetExpirationTime() {
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        long expirationTime = jwtUtil.getExpirationTime(token);
        assertTrue(expirationTime > 0);
        assertTrue(expirationTime > 1700 * 1000 && expirationTime <= 1800 * 1000);
    }

    @Test
    @DisplayName("测试不同用户生成不同 Token")
    void testDifferentUsersGenerateDifferentTokens() {
        String token1 = jwtUtil.generateAccessToken(1L, "user1", Arrays.asList("USER"));
        String token2 = jwtUtil.generateAccessToken(2L, "user2", Arrays.asList("ADMIN"));
        assertNotEquals(token1, token2);
    }

    @Test
    @DisplayName("测试同一用户多次生成 Token")
    void testSameUserGenerateDifferentTokens() throws InterruptedException {
        String token1 = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        Thread.sleep(1100);
        String token2 = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, TEST_ROLES);
        assertNotEquals(token1, token2);
        assertEquals(jwtUtil.getUserIdFromToken(token1), jwtUtil.getUserIdFromToken(token2));
        assertEquals(jwtUtil.getUsernameFromToken(token1), jwtUtil.getUsernameFromToken(token2));
    }

    @Test
    @DisplayName("测试包含特殊字符的用户名")
    void testSpecialCharacterUsername() {
        String specialUsername = "user@test.com_123";
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, specialUsername, TEST_ROLES);
        String extractedUsername = jwtUtil.getUsernameFromToken(token);
        assertEquals(specialUsername, extractedUsername);
    }

    @Test
    @DisplayName("测试大量角色列表")
    void testLargeRolesList() {
        List<String> manyRoles = Arrays.asList(
            "ROLE_ADMIN", "ROLE_USER", "ROLE_MANAGER",
            "ROLE_EDITOR", "ROLE_VIEWER", "ROLE_AUDITOR"
        );
        String token = jwtUtil.generateAccessToken(TEST_USER_ID, TEST_USERNAME, manyRoles);
        List<String> extractedRoles = jwtUtil.getRolesFromToken(token);
        assertEquals(manyRoles, extractedRoles);
    }
}
