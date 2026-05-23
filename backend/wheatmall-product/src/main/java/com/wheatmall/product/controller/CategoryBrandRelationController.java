package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.service.CategoryBrandRelationService;
import com.wheatmall.product.query.CategoryBrandRelationQueryDTO;
import com.wheatmall.product.vo.CategoryBrandRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌分类关联控制器
 */
@RestController
@RequestMapping(ProductServiceUris.PRODUCT_CATEGORY_BRAND_RELATION_SERVICE)
public class CategoryBrandRelationController {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 分页查询关联关系
     */
    @GetMapping(ProductServiceUris.ProductCategoryBrandRelation.PAGE)
    public R<PageData<CategoryBrandRelationVO>> list(CategoryBrandRelationQueryDTO query) {
        return R.ok(categoryBrandRelationService.page(query));
    }

    /**
     * 根据分类ID查询关联品牌
     */
    @GetMapping(ProductServiceUris.ProductCategoryBrandRelation.BRANDS_BY_CAT)
    public R<List<CategoryBrandRelationVO>> brandsByCat(@PathVariable Long catId) {
        // TODO: 添加 listByCatelogId 方法到 CategoryBrandRelationService
        return R.ok(java.util.Collections.emptyList());
    }

    /**
     * 保存品牌分类关联
     */
    @PostMapping(ProductServiceUris.ProductCategoryBrandRelation.SAVE)
    public R<Void> save(@RequestBody CategoryBrandRelationVO vo) {
        categoryBrandRelationService.saveDetail(vo);
        return R.ok();
    }
}
