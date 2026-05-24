package com.youhaowu.common.constant;

/**
 * Ware 服务 URI 常量
 */
public final class WareServiceUris {

    private WareServiceUris() {
    }

    // ==================== 采购单 ====================
    public static final class Purchase {
        public static final String BASE_URL = "/api/ware/purchase";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
        public static final String UNRECEIVE_LIST = BASE_URL + "/unreceive/list";
        public static final String MERGE = BASE_URL + "/merge";
        public static final String RECEIVED = BASE_URL + "/received";
        public static final String DONE = BASE_URL + "/done";
    }

    // ==================== 采购需求 ====================
    public static final class PurchaseDetail {
        public static final String BASE_URL = "/api/ware/purchasedetail";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 仓库信息 ====================
    public static final class WareInfo {
        public static final String BASE_URL = "/api/ware/wareinfo";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 库存工作单 ====================
    public static final class WareOrderTask {
        public static final String BASE_URL = "/api/ware/wareordertask";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 库存工作单明细 ====================
    public static final class WareOrderTaskDetail {
        public static final String BASE_URL = "/api/ware/wareordertaskdetail";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 商品库存 ====================
    public static final class WareSku {
        public static final String BASE_URL = "/api/ware/waresku";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{id}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
        public static final String HAS_STOCK = BASE_URL + "/hasStock";
    }
}
