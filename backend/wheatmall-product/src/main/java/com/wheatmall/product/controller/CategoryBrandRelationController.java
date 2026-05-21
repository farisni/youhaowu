package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌与分类关联关系控制器
 * 管理品牌与分类的关联关系，提供查询品牌关联分类及获取分类下品牌列表的接口
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class CategoryBrandRelationController {
}
