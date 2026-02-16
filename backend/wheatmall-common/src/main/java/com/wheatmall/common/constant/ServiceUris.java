package com.wheatmall.common.constant;

/**
 * 服务URI常量类
 * 统一管理所有服务的API路径，便于维护和修改
 */
public final class ServiceUris {

    private ServiceUris() {
        // 私有构造器，防止实例化
    }

    // ==================== 服务根路径 ====================
    
    /** Product服务根路径 */
    public static final String PRODUCT_SERVICE = "/api/product";
    
    /** Order服务根路径 */
    public static final String ORDER_SERVICE = "/api/order";
    
    /** User服务根路径 */
    public static final String USER_SERVICE = "/api/user";
    
    // ==================== Product服务相关URI ====================
    
    public static final class Product {
        
        /** 根据ID查询商品 */
        public static final String GET_BY_ID = PRODUCT_SERVICE + "/{id}";
        
        /** 供内部服务调用 - 根据ID查询商品 */
        public static final String GET_BY_ID_INTERNAL = PRODUCT_SERVICE + "/get/{id}";
        
        /** 查询所有商品列表 */
        public static final String LIST = PRODUCT_SERVICE + "/list";
        
        /** 创建商品 */
        public static final String CREATE = PRODUCT_SERVICE;
        
        /** 更新商品 */
        public static final String UPDATE = PRODUCT_SERVICE + "/{id}";
        
        /** 删除商品 */
        public static final String DELETE = PRODUCT_SERVICE + "/{id}";
        
        /** 扣减库存 */
        public static final String DEDUCT_STOCK = PRODUCT_SERVICE + "/{id}/deduct-stock";
    }

    // ==================== Order服务相关URI ====================
    
    public static final class Order {
        
        /** 同步获取商品信息（调用Product服务） */
        public static final String GET_PRODUCT = ORDER_SERVICE + "/product/{productId}";
        
        /** 异步获取商品信息（调用Product服务） */
        public static final String GET_PRODUCT_ASYNC = ORDER_SERVICE + "/product/async/{productId}";
        
        /** 获取商品列表（调用Product服务） */
        public static final String GET_PRODUCT_LIST = ORDER_SERVICE + "/products";
        
        /** 创建订单 */
        public static final String CREATE = ORDER_SERVICE + "/create";
    }

    // ==================== User服务相关URI（预留） ====================
    
    public static final class User {
        
        /** 根据ID查询用户 */
        public static final String GET_BY_ID = USER_SERVICE + "/{id}";
        
        /** 查询当前登录用户 */
        public static final String GET_CURRENT = USER_SERVICE + "/current";
    }
}
