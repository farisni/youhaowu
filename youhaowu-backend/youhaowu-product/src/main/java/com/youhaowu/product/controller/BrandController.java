package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.query.BrandQueryDTO;
import com.youhaowu.product.service.BrandService;
import com.youhaowu.product.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

/**
 * 品牌管理控制器
 */
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌列表
     */
    @GetMapping(ProductServiceUris.ProductBrand.PAGE)
    public PageData<BrandVO> list(BrandQueryDTO query) {
        return brandService.page(query);
    }

    /**
     * 根据ID查询品牌信息
     */
    @GetMapping(ProductServiceUris.ProductBrand.INFO)
    public BrandVO info(@PathVariable Long brandId) {
        return brandService.getById(brandId);
    }

    /**
     * 保存品牌
     */
    @PostMapping(ProductServiceUris.ProductBrand.SAVE)
    public Integer save(@RequestBody BrandVO vo) {
        return brandService.save(vo);
    }

    /**
     * 更新品牌信息
     */
    @PostMapping(ProductServiceUris.ProductBrand.UPDATE)
    public Object update(@PathVariable Long id, @RequestBody BrandVO vo) {
        brandService.updateDetail(id, vo);
        
        return null;
    }

    /**
     * 批量删除品牌
     */
    @PostMapping(ProductServiceUris.ProductBrand.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return brandService.removeByIds(ids);
    }
}
