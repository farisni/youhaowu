package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品属性控制器
 * 提供商品属性管理，支持分类ID查询属性、SPU规格回显以及商品规格参数的更新操作
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_CATEGORY_SERVICE)
public class AttrController {
    // TODO: GET /list/{catelogId} 查询分类下的属性列表
    // TODO: GET /info/{attrId} 查询属性详情
    // TODO: POST /save 保存属性
    // TODO: POST /update 修改属性
    // TODO: POST /delete 删除属性
}
