package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
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
    public R<PageData<BrandVO>> list(BrandQueryDTO query) {
        return R.ok(brandService.page(query));
    }

    /**
     * 根据ID查询品牌信息
     */
    @GetMapping(ProductServiceUris.ProductBrand.INFO)
    public R<BrandVO> info(@PathVariable Long brandId) {
        return R.ok(brandService.getById(brandId));
    }

    /**
     * 保存品牌
     */
    @PostMapping(ProductServiceUris.ProductBrand.SAVE)
    public R<Integer> save(@RequestBody BrandVO vo) {
        return R.ok(brandService.save(vo));
    }

    /**
     * 更新品牌信息
     */
    @PostMapping(ProductServiceUris.ProductBrand.UPDATE)
    public R<Void> update(@PathVariable Long id, @RequestBody BrandVO vo) {
        brandService.updateDetail(id, vo);
        return R.ok();
    }

    /**
     * 批量删除品牌
     */
    @PostMapping(ProductServiceUris.ProductBrand.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(brandService.removeByIds(ids));
    }
}
