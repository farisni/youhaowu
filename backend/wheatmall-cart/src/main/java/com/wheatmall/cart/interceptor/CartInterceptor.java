package com.wheatmall.cart.interceptor;

import com.wheatmall.common.constant.CartConstant;
import com.wheatmall.common.to.cart.UserInfoTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

public class CartInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoTO> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        UserInfoTO user = new UserInfoTO();
        //  从 Header 取登录用户ID（占位，后续对接 auth JWT）
        String userIdHeader = request.getHeader("X-User-Id");
        if (StringUtils.isNotBlank(userIdHeader)) {
            user.setUserId(Long.valueOf(userIdHeader));
        }
        //  从 Cookie 取游客标识 user-key
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CartConstant.TEMP_USER_COOKIE_NAME.equals(cookie.getName())) {
                    user.setUserKey(cookie.getValue());
                    user.setTempUser(true);
                    break;
                }
            }
        }
        //  无游客标识，生成新 UUID
        if (StringUtils.isBlank(user.getUserKey())) {
            user.setUserKey(UUID.randomUUID().toString());
        }
        threadLocal.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        //  清理 ThreadLocal
        threadLocal.remove();
    }
}
