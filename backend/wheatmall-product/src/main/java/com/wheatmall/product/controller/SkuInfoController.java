package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.query.SkuQueryDTO;
import com.wheatmall.product.service.SkuInfoService;
import com.wheatmall.product.vo.SkuInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SKU信息控制器
 */
@RestController
@RequestMapping(ProductServiceUris.PRODUCT_SKU_SERVICE)
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
