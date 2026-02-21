package com.wheatmall.authadmin.security.jwt;

import com.wheatmall.authadmin.exception.TokenInvalidException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT 工具类
 * 
 * 功能：
 * 1. 生成 Access Token（短期有效）
 * 2. 生成 Refresh Token（长期有效）
 * 3. 解析和验证 Token
 * 4. 从 Token 提取用户信息
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * JWT 密钥（至少32字符）
     */
    @Value("${jwt.secret:your-256-bit-secret-your-256-bit-secret-key}")
    private String secret;

    /**
     * Access Token 过期时间（秒），默认30分钟
     */
    @Value("${jwt.access-token-expiration:1800}")
    private Long accessTokenExpiration;

    /**
     * Refresh Token 过期时间（秒），默认7天
     */
    @Value("${jwt.refresh-token-expiration:604800}")
    private Long refreshTokenExpiration;

    /**
     * Token 类型标识
     */
    private static final String TOKEN_TYPE_ACCESS = "ACCESS";
    private static final String TOKEN_TYPE_REFRESH = "REFRESH";

    /**
     * 获取签名密钥
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Access Token（访问令牌）
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param roles    角色列表
     * @return JWT Token
     */
    public String generateAccessToken(Long userId, String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("roles", roles);
        claims.put("tokenType", TOKEN_TYPE_ACCESS);

        return generateToken(claims, accessTokenExpiration);
    }

    /**
     * 生成 Refresh Token（刷新令牌）
     *
     * @param userId 用户ID
     * @return JWT Token
     */
    public String generateRefreshToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("tokenType", TOKEN_TYPE_REFRESH);

        return generateToken(claims, refreshTokenExpiration);
    }

    /**
     * 生成 JWT Token（通用方法）
     *
     * @param claims     声明信息
     * @param expiration 过期时间（秒）
     * @return JWT Token
     */
    private String generateToken(Map<String, Object> claims, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("userId").toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 解析 Token 获取 Claims
     *
     * @param token JWT Token
     * @return Claims
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("Token 已过期: {}", e.getMessage());
            throw new TokenInvalidException("Token 已过期");
        } catch (UnsupportedJwtException e) {
            log.warn("不支持的 Token: {}", e.getMessage());
            throw new TokenInvalidException("Token 格式错误");
        } catch (MalformedJwtException e) {
            log.warn("Token 格式错误: {}", e.getMessage());
            throw new TokenInvalidException("Token 格式错误");
        } catch (SignatureException e) {
            log.warn("Token 签名无效: {}", e.getMessage());
            throw new TokenInvalidException("Token 签名无效");
        } catch (IllegalArgumentException e) {
            log.warn("Token 为空或非法: {}", e.getMessage());
            throw new TokenInvalidException("Token 无效");
        }
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token JWT Token
     * @return true-有效，false-无效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (TokenInvalidException e) {
            return false;
        }
    }

    /**
     * 从 Token 获取用户ID
     *
     * @param token JWT Token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(claims.get("userId").toString());
    }

    /**
     * 从 Token 获取用户名
     *
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 从 Token 获取角色列表
     *
     * @param token JWT Token
     * @return 角色列表
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("roles", List.class);
    }

    /**
     * 获取 Token 过期时间
     *
     * @param token JWT Token
     * @return 过期日期
     */
    public Date getExpirationDate(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    /**
     * 获取 Token 剩余有效时间（毫秒）
     *
     * @param token JWT Token
     * @return 剩余毫秒数
     */
    public long getExpirationTime(String token) {
        Date expiration = getExpirationDate(token);
        return expiration.getTime() - System.currentTimeMillis();
    }

    /**
     * 检查 Token 是否为 Access Token
     *
     * @param token JWT Token
     * @return true-是 Access Token
     */
    public boolean isAccessToken(String token) {
        Claims claims = parseToken(token);
        return TOKEN_TYPE_ACCESS.equals(claims.get("tokenType"));
    }

    /**
     * 检查 Token 是否为 Refresh Token
     *
     * @param token JWT Token
     * @return true-是 Refresh Token
     */
    public boolean isRefreshToken(String token) {
        Claims claims = parseToken(token);
        return TOKEN_TYPE_REFRESH.equals(claims.get("tokenType"));
    }

    // ========== Setter 方法（用于测试）==========

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setAccessTokenExpiration(Long accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
}
