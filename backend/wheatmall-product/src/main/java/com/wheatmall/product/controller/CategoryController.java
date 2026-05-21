package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.dto.CategoryQueryDTO;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品三级分类控制器
 * 实现商品三级分类管理，支持树形结构获取、分类信息查询、修改以及逻辑删除功能
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_CATEGORY_SERVICE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类树
     * 返回三级分类树形结构，一级分类包含子分类，子分类嵌套孙分类
     */
    @GetMapping(ServiceUris.ProductCategory.LIST_TREE)
    public R<List<CategoryVO>> list() {
        return R.ok(categoryService.listWithTree());
    }

    /**
     * 分页查询分类
     * 支持按名称模糊查询和关键字搜索
     */
    @PostMapping(ServiceUris.ProductCategory.PAGE)
    public R<PageData<CategoryVO>> page(@Valid CategoryQueryDTO query) {
        return R.ok(categoryService.page(query));
    }
}
