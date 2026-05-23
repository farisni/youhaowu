package com.wheatmall.common.constant;

/**
 * Order服务URI常量类
 */
public final class OrderServiceUris {

    private OrderServiceUris() {
    }

    public static final String BASE_URL = "/api/order";

    // ==================== Order接口 ====================
    public static final class Order {
        public static final String GET_PRODUCT = BASE_URL + "/product/{productId}";
        public static final String GET_PRODUCT_ASYNC = BASE_URL + "/product/async/{productId}";
        public static final String GET_PRODUCT_LIST = BASE_URL + "/products";
        public static final String CREATE = BASE_URL + "/create";
    }
}
