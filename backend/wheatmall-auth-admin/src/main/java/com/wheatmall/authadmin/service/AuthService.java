package com.wheatmall.authadmin.service;

import com.wheatmall.authadmin.dto.LoginRequest;
import com.wheatmall.authadmin.vo.LoginResponse;
import com.wheatmall.authadmin.vo.UserInfoVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout(String token);

    LoginResponse refreshToken(String refreshToken);

    UserInfoVO getCurrentUser();
}
