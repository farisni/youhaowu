package com.wheatmall.common.constant;

/**
 * 服务URI常量类
 * 统一管理所有服务的API路径，便于维护和修改
 */
public final class ProductServiceUris {

    private ProductServiceUris() {
        // 私有构造器，防止实例化
    }

    // ==================== 服务根路径 ====================
    
    /** 商品分类服务根路径 */
    public static final String PRODUCT_CATEGORY_SERVICE = "/api/product/category";

    /** Product 商品服务根路径 */
    public static final String PRODUCT_ROOT = "/api/product";

    /** Order服务根路径 */
    public static final String ORDER_SERVICE = "/api/order";
    
    /** User服务根路径 */
    public static final String USER_SERVICE = "/api/user";

    // ==================== 各模块根路径 ====================

    /** 品牌管理根路径 */
    public static final String PRODUCT_BRAND_SERVICE = PRODUCT_ROOT + "/brand";

    /** 属性管理根路径 */
    public static final String PRODUCT_ATTR_SERVICE = PRODUCT_ROOT + "/attr";

    /** 属性分组根路径 */
    public static final String PRODUCT_ATTRGROUP_SERVICE = PRODUCT_ROOT + "/attrgroup";

    /** SPU 信息根路径 */
    public static final String PRODUCT_SPU_SERVICE = PRODUCT_ROOT + "/spuinfo";

    /** SKU 信息根路径 */
    public static final String PRODUCT_SKU_SERVICE = PRODUCT_ROOT + "/skuinfo";

    /** 分类品牌关联根路径 */
    public static final String PRODUCT_CATEGORY_BRAND_RELATION_SERVICE = PRODUCT_ROOT + "/categorybrandrelation";

    /** 评论回复根路径 */
    public static final String PRODUCT_COMMENT_REPLAY_SERVICE = PRODUCT_ROOT + "/commentreplay";

    /** 商品属性值根路径 */
    public static final String PRODUCT_ATTR_VALUE_SERVICE = PRODUCT_ROOT + "/productattrvalue";


    // ==================== 商品分类相关URI ====================
    public static final class ProductCategory {
        public static final String LIST_TREE = "/list/tree";
        public static final String PAGE = "/list";
        public static final String PARENT_BY_ID = "/parent/{parentId}";
        public static final String DELETE = "/delete/{id}";
        public static final String UPDATE = "/update/{id}";
    }


    // ==================== Order服务相关URI ====================
    public static final class Order {
        public static final String GET_PRODUCT = ORDER_SERVICE + "/product/{productId}";
        public static final String GET_PRODUCT_ASYNC = ORDER_SERVICE + "/product/async/{productId}";
        public static final String GET_PRODUCT_LIST = ORDER_SERVICE + "/products";
        public static final String CREATE = ORDER_SERVICE + "/create";
    }

    // ==================== User服务相关URI（预留） ====================
    public static final class User {
        public static final String GET_BY_ID = USER_SERVICE + "/{id}";
        public static final String GET_CURRENT = USER_SERVICE + "/current";
    }

    // ==================== 品牌管理 ====================
    public static final class ProductBrand {
        public static final String PAGE = "/list";
        public static final String INFO = "/info/{brandId}";
        public static final String SAVE = "/save";
        public static final String UPDATE = "/update/{id}";
        public static final String DELETE = "/delete/{id}";
    }

    // ==================== 属性管理 ====================
    public static final class ProductAttr {
        public static final String BASE_PAGE = "/base/list/{catelogId}";
        public static final String SALE_PAGE = "/sale/list/{catelogId}";
        public static final String INFO = "/info/{attrId}";
        public static final String SAVE = "/save";
        public static final String UPDATE = "/update/{id}";
        public static final String DELETE = "/delete/{id}";
        public static final String RELATION_PAGE = "/{attrgroupId}/attr/relation";
        public static final String NO_RELATION_PAGE = "/{attrgroupId}/no/relation";
        public static final String DELETE_RELATION = "/relation/delete";
        public static final String SAVE_RELATION = "/relation";
    }

    // ==================== 属性分组 ====================
    public static final class ProductAttrGroup {
        public static final String PAGE = "/list/{catelogId}";
        public static final String INFO = "/info/{attrGroupId}";
        public static final String SAVE = "/save";
        public static final String UPDATE = "/update/{id}";
        public static final String DELETE = "/delete/{id}";
        public static final String WITH_ATTRS = "/{catelogId}/withattr";
    }

    // ==================== SPU 管理 ====================
    public static final class ProductSpu {
        public static final String PAGE = "/list";
        public static final String INFO = "/info/{spuId}";
        public static final String SAVE = "/save";
        public static final String UPDATE = "/update/{id}";
        public static final String DELETE = "/delete/{id}";
        public static final String UP = "/{spuId}/up";
    }

    // ==================== SKU 管理 ====================
    public static final class ProductSku {
        public static final String PAGE = "/list";
        public static final String INFO = "/info/{skuId}";
        public static final String SAVE = "/save";
    }

    // ==================== 分类品牌关联 ====================
    public static final class ProductCategoryBrandRelation {
        public static final String PAGE = "/list";
        public static final String BRANDS_BY_CAT = "/catelog/{catId}";
        public static final String SAVE = "/save";
    }

    // ==================== 评论回复 ====================
    public static final class ProductCommentReplay {
        public static final String PAGE = "/list";
    }

    // ==================== 商品属性值 ====================
    public static final class ProductAttrValue {
        public static final String PAGE = "/list";
        public static final String LIST_BY_SPU = "/{spuId}";
    }
}
