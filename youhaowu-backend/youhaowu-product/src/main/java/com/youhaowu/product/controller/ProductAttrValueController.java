package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.service.ProductAttrValueService;
import com.youhaowu.product.query.ProductAttrValueQueryDTO;
import com.youhaowu.product.vo.ProductAttrValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SPU属性值控制器
 */
@RestController
public class ProductAttrValueController {

    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 分页查询属性值
     */
    @GetMapping(ProductServiceUris.ProductAttrValue.PAGE)
    public PageData<ProductAttrValueVO> list(ProductAttrValueQueryDTO query) {
        return productAttrValueService.page(query);
    }

    /**
     * 根据SPU ID查询属性值列表
     */
    @GetMapping(ProductServiceUris.ProductAttrValue.LIST_BY_SPU)
    public List<ProductAttrValueVO> listBySpuId(@PathVariable Long spuId) {
        return productAttrValueService.listBySpuId(spuId);
    }
}
