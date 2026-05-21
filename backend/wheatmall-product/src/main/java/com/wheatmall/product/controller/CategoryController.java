package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品三级分类控制器
 * 实现商品三级分类管理，支持树形结构获取、分类信息查询、修改以及逻辑删除功能
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class CategoryController {
}
