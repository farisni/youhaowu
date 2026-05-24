package com.youhaowu.auth.service;

import com.youhaowu.auth.dto.LoginRequest;
import com.youhaowu.auth.vo.LoginResponse;
import com.youhaowu.auth.vo.UserInfoVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout(String token);

    LoginResponse refreshToken(String refreshToken);

    UserInfoVO getCurrentUser();
}
