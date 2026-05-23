package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.service.ProductAttrValueService;
import com.wheatmall.product.query.ProductAttrValueQueryDTO;
import com.wheatmall.product.vo.ProductAttrValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SPU属性值控制器
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_ATTR_VALUE_SERVICE)
public class ProductAttrValueController {

    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 分页查询属性值
     */
    @GetMapping(ServiceUris.ProductAttrValue.PAGE)
    public R<PageData<ProductAttrValueVO>> list(ProductAttrValueQueryDTO query) {
        return R.ok(productAttrValueService.page(query));
    }

    /**
     * 根据SPU ID查询属性值列表
     */
    @GetMapping(ServiceUris.ProductAttrValue.LIST_BY_SPU)
    public R<List<ProductAttrValueVO>> listBySpuId(@PathVariable Long spuId) {
        return R.ok(productAttrValueService.listBySpuId(spuId));
    }
}
