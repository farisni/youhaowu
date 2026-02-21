package com.wheatmall.authadmin.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 登录响应 VO
 */
@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String tokenType;
    private UserInfoVO userInfo;
}
