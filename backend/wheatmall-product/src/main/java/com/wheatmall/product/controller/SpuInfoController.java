package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.service.SpuInfoService;
import com.wheatmall.product.query.SpuQueryDTO;
import com.wheatmall.product.vo.SpuInfoVO;
import com.wheatmall.product.vo.SpuSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SPU信息控制器
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SPU_SERVICE)
public class SpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 分页条件查询SPU
     */
    @GetMapping(ServiceUris.ProductSpu.PAGE)
    public R<PageData<SpuInfoVO>> list(SpuQueryDTO query) {
        return R.ok(spuInfoService.page(query));
    }

    /**
     * 根据ID查询SPU信息
     */
    @GetMapping(ServiceUris.ProductSpu.INFO)
    public R<SpuInfoVO> info(@PathVariable Long spuId) {
        return R.ok(spuInfoService.getVOById(spuId));
    }

    /**
     * 保存SPU
     */
    @PostMapping(ServiceUris.ProductSpu.SAVE)
    public R<Void> save(@RequestBody SpuSaveVO vo) {
        spuInfoService.saveSupInfo(vo);
        return R.ok();
    }

    /**
     * SPU上架
     */
    @PostMapping(ServiceUris.ProductSpu.UP)
    public R<Void> up(@PathVariable Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }
}
