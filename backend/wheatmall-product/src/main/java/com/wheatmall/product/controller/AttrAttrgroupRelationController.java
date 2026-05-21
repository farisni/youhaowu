package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 属性与属性分组关联关系控制器
 * 处理属性与属性分组之间的关联关系，支持列表查询、信息查看、保存及删除
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class AttrAttrgroupRelationController {
    // TODO: GET /list/{attrgroupId} 查询分组关联的属性
    // TODO: POST /save 保存关联关系
    // TODO: POST /delete 删除关联关系
}
