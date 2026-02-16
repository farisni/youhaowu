package com.wheatmall.common.constant;

/**
 * 服务URI常量类
 * 统一管理所有服务的API路径，便于维护和修改
 */
public final class ServiceUris {

    private ServiceUris() {
        // 私有构造器，防止实例化
    }

    /**
     * Product服务相关URI
     */
    public static final class Product {
        
        /** 服务根路径 */
        public static final String SERVICE = "/api/product";
        
        /** 根据ID查询商品 */
        public static final String GET_BY_ID = SERVICE + "/{id}";
        
        /** 供内部服务调用 - 根据ID查询商品 */
        public static final String GET_BY_ID_INTERNAL = SERVICE + "/get/{id}";
        
        /** 查询所有商品列表 */
        public static final String LIST = SERVICE + "/list";
        
        /** 创建商品 */
        public static final String CREATE = SERVICE;
        
        /** 更新商品 */
        public static final String UPDATE = SERVICE + "/{id}";
        
        /** 删除商品 */
        public static final String DELETE = SERVICE + "/{id}";
        
        /** 扣减库存 */
        public static final String DEDUCT_STOCK = SERVICE + "/{id}/deduct-stock";
    }

    /**
     * Order服务相关URI
     */
    public static final class Order {
        
        /** 服务根路径 */
        public static final String SERVICE = "/api/order";
        
        /** 同步获取商品信息（调用Product服务） */
        public static final String GET_PRODUCT = SERVICE + "/product/{productId}";
        
        /** 异步获取商品信息（调用Product服务） */
        public static final String GET_PRODUCT_ASYNC = SERVICE + "/product/async/{productId}";
        
        /** 获取商品列表（调用Product服务） */
        public static final String GET_PRODUCT_LIST = SERVICE + "/products";
        
        /** 创建订单 */
        public static final String CREATE = SERVICE + "/create";
    }

    /**
     * User服务相关URI（预留）
     */
    public static final class User {
        
        /** 服务根路径 */
        public static final String SERVICE = "/api/user";
        
        /** 根据ID查询用户 */
        public static final String GET_BY_ID = SERVICE + "/{id}";
        
        /** 查询当前登录用户 */
        public static final String GET_CURRENT = SERVICE + "/current";
    }
}
