package com.youhaowu.common.constant;

/**
 * User服务URI常量类
 */
public final class UserServiceUris {

    private UserServiceUris() {
    }

    public static final String BASE_URL = "/api/user";

    // ==================== User接口 ====================
    public static final class User {
        public static final String GET_BY_ID = BASE_URL + "/{id}";
        public static final String GET_CURRENT = BASE_URL + "/current";
    }
}
