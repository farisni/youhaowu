package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.product.query.SkuQueryDTO;
import com.youhaowu.product.service.SkuInfoService;
import com.youhaowu.product.vo.SkuInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SKU信息控制器
 */
@RestController
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 分页条件查询SKU
     */
    @GetMapping(ProductServiceUris.ProductSku.PAGE)
    public R<PageData<SkuInfoVO>> list(SkuQueryDTO query) {
        return R.ok(skuInfoService.page(query));
    }

    /**
     * 根据ID查询SKU信息
     */
    @GetMapping(ProductServiceUris.ProductSku.INFO)
    public R<SkuInfoVO> info(@PathVariable Long skuId) {
        return R.ok(skuInfoService.getVOById(skuId));
    }

    /**
     * 保存SKU信息
     */
    @PostMapping(ProductServiceUris.ProductSku.SAVE)
    public R<Void> save(@RequestBody SkuInfoVO vo) {
        skuInfoService.saveSkuInfo(vo);
        return R.ok();
    }
}
