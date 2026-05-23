package com.wheatmall.auth.service;

import com.wheatmall.auth.dto.LoginRequest;
import com.wheatmall.auth.vo.LoginResponse;
import com.wheatmall.auth.vo.UserInfoVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout(String token);

    LoginResponse refreshToken(String refreshToken);

    UserInfoVO getCurrentUser();
}
