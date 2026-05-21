package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SKU信息控制器
 * 处理SKU信息管理，包括分页查询、价格信息获取以及SKU实体的保存、更新与删除
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_CATEGORY_SERVICE)
public class SkuInfoController {
    // TODO: GET /list 分页查询SKU
    // TODO: GET /info/{skuId} SKU详情
    // TODO: POST /save 新增SKU
    // TODO: POST /update 更新SKU
    // TODO: POST /delete 删除SKU
}
