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

    // ==================== 对 Product 模块的调用 ====================
    public static final class Product {
        public static final String GET_BY_ID_INTERNAL = "/api/product/category/get/{id}";
        public static final String LIST = "/api/product/category/list";
    }
}
