package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.product.service.CategoryBrandRelationService;
import com.youhaowu.product.query.CategoryBrandRelationQueryDTO;
import com.youhaowu.product.vo.CategoryBrandRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌分类关联控制器
 */
@RestController
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
