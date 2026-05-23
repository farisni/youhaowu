package com.wheatmall.common.constant;

/**
 * Order 服务 URI 常量
 */
public final class OrderServiceUris {

    private OrderServiceUris() {
    }

    // ==================== 订单 ====================
    public static final class Order {
        public static final String BASE_URL = "/api/order/order";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 订单项 ====================
    public static final class OrderItem {
        public static final String BASE_URL = "/api/order/orderitem";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 订单操作历史 ====================
    public static final class OrderOperateHistory {
        public static final String BASE_URL = "/api/order/orderoperatehistory";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 退货申请 ====================
    public static final class OrderReturnApply {
        public static final String BASE_URL = "/api/order/orderreturnapply";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 退货原因 ====================
    public static final class OrderReturnReason {
        public static final String BASE_URL = "/api/order/orderreturnreason";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 订单配置 ====================
    public static final class OrderSetting {
        public static final String BASE_URL = "/api/order/ordersetting";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 支付信息 ====================
    public static final class PaymentInfo {
        public static final String BASE_URL = "/api/order/paymentinfo";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
    }

    // ==================== 退款信息 ====================
    public static final class RefundInfo {
        public static final String BASE_URL = "/api/order/refundinfo";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
    }

    // ==================== MQ 消息 ====================
    public static final class MqMessage {
        public static final String BASE_URL = "/api/order/mqmessage";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
    }

    // ==================== 对 Product 模块的调用 ====================
    public static final class Product {
        public static final String GET_BY_ID_INTERNAL = "/api/product/category/get/{id}";
        public static final String LIST = "/api/product/category/list";
    }
}
