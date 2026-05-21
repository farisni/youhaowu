package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SPU信息控制器
 * 管理SPU信息，包含SPU保存、列表查询、删除及商品上架功能的接口定义
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_CATEGORY_SERVICE)
public class SpuInfoController {
    // TODO: GET /list SPU列表
    // TODO: POST /save 新增SPU
    // TODO: POST /delete 删除SPU
    // TODO: POST /{spuId}/up 商品上架
}
