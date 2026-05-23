package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.query.BrandQueryDTO;
import com.wheatmall.product.service.BrandService;
import com.wheatmall.product.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 品牌管理控制器
 */
@RestController
@RequestMapping(ProductServiceUris.PRODUCT_BRAND_SERVICE)
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
     * 删除品牌
     */
    @PostMapping(ProductServiceUris.ProductBrand.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(brandService.removeById(id));
    }
}
