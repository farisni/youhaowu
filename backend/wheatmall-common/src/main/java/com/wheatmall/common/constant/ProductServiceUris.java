package com.wheatmall.common.constant;

/**
 * 服务URI常量类
 * 统一管理所有服务的API路径，便于维护和修改
 */
public final class ProductServiceUris {

    private ProductServiceUris() {
    }

    // ==================== 商品分类 ====================
    public static final class ProductCategory {
        public static final String BASE_URL = "/api/product/category";
        public static final String LIST_TREE = BASE_URL + "/list/tree";
        public static final String PAGE = BASE_URL + "/list";
        public static final String PARENT_BY_ID = BASE_URL + "/parent/{parentId}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
        public static final String UPDATE = BASE_URL + "/update/{id}";
    }

    // ==================== Product服务相关URI（已弃用） ====================
    @Deprecated
    public static final class Product {
        public static final String GET_BY_ID = ProductCategory.BASE_URL + "/{id}";
        public static final String CREATE = ProductCategory.BASE_URL;
        public static final String UPDATE = ProductCategory.BASE_URL + "/{id}";
        public static final String DELETE = ProductCategory.BASE_URL + "/{id}";
        public static final String DEDUCT_STOCK = ProductCategory.BASE_URL + "/{id}/deduct-stock";
    }

    // ==================== 品牌管理 ====================
    public static final class ProductBrand {
        public static final String BASE_URL = "/api/product/brand";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{brandId}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
    }

    // ==================== 属性管理 ====================
    public static final class ProductAttr {
        public static final String BASE_URL = "/api/product/attr";
        public static final String BASE_PAGE = BASE_URL + "/base/list/{catelogId}";
        public static final String SALE_PAGE = BASE_URL + "/sale/list/{catelogId}";
        public static final String INFO = BASE_URL + "/info/{attrId}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
        public static final String RELATION_PAGE = BASE_URL + "/{attrgroupId}/attr/relation";
        public static final String NO_RELATION_PAGE = BASE_URL + "/{attrgroupId}/no/relation";
        public static final String DELETE_RELATION = BASE_URL + "/relation/delete";
        public static final String SAVE_RELATION = BASE_URL + "/relation";
    }

    // ==================== 属性分组 ====================
    public static final class ProductAttrGroup {
        public static final String BASE_URL = "/api/product/attrgroup";
        public static final String PAGE = BASE_URL + "/list/{catelogId}";
        public static final String INFO = BASE_URL + "/info/{attrGroupId}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
        public static final String WITH_ATTRS = BASE_URL + "/{catelogId}/withattr";
    }

    // ==================== SPU 管理 ====================
    public static final class ProductSpu {
        public static final String BASE_URL = "/api/product/spuinfo";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{spuId}";
        public static final String SAVE = BASE_URL + "/save";
        public static final String UPDATE = BASE_URL + "/update/{id}";
        public static final String DELETE = BASE_URL + "/delete/{id}";
        public static final String UP = BASE_URL + "/{spuId}/up";
    }

    // ==================== SKU 管理 ====================
    public static final class ProductSku {
        public static final String BASE_URL = "/api/product/skuinfo";
        public static final String PAGE = BASE_URL + "/list";
        public static final String INFO = BASE_URL + "/info/{skuId}";
        public static final String SAVE = BASE_URL + "/save";
    }

    // ==================== 分类品牌关联 ====================
    public static final class ProductCategoryBrandRelation {
        public static final String BASE_URL = "/api/product/categorybrandrelation";
        public static final String PAGE = BASE_URL + "/list";
        public static final String BRANDS_BY_CAT = BASE_URL + "/catelog/{catId}";
        public static final String SAVE = BASE_URL + "/save";
    }

    // ==================== 评论回复 ====================
    public static final class ProductCommentReplay {
        public static final String BASE_URL = "/api/product/commentreplay";
        public static final String PAGE = BASE_URL + "/list";
    }

    // ==================== 商品属性值 ====================
    public static final class ProductAttrValue {
        public static final String BASE_URL = "/api/product/productattrvalue";
        public static final String PAGE = BASE_URL + "/list";
        public static final String LIST_BY_SPU = BASE_URL + "/{spuId}";
    }

}
