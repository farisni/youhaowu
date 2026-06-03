package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.service.SpuInfoService;
import com.youhaowu.product.query.SpuQueryDTO;
import com.youhaowu.product.vo.SpuInfoVO;
import com.youhaowu.product.vo.SpuSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * SPU信息控制器
 */
@RestController
public class SpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 分页条件查询SPU
     */
    @GetMapping(ProductServiceUris.ProductSpu.PAGE)
    public PageData<SpuInfoVO> list(SpuQueryDTO query) {
        return spuInfoService.page(query);
    }

    /**
     * 根据ID查询SPU信息
     */
    @GetMapping(ProductServiceUris.ProductSpu.INFO)
    public SpuInfoVO info(@PathVariable Long spuId) {
        return spuInfoService.getVOById(spuId);
    }

    /**
     * 保存SPU
     */
    @PostMapping(ProductServiceUris.ProductSpu.SAVE)
    public Object save(@RequestBody SpuSaveVO vo) {
        spuInfoService.saveSupInfo(vo);
        
        return null;
    }

    /**
     * SPU上架
     */
    @PostMapping(ProductServiceUris.ProductSpu.UP)
    public Object up(@PathVariable Long spuId) {
        spuInfoService.up(spuId);
        
        return null;
    }
}
