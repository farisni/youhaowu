package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌控制器
 * 负责品牌信息管理，支持品牌列表、信息查看、保存及基于JSR303校验的状态更新
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class BrandController {
}
