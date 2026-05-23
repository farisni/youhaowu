package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.dto.CategoryQueryDTO;
import com.wheatmall.product.dto.CategoryUpdateDTO;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品三级分类控制器
 * 实现商品三级分类管理，支持树形结构获取、分类信息查询、修改以及逻辑删除功能
 */
@RestController
@RequestMapping(ProductServiceUris.PRODUCT_CATEGORY_SERVICE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类树
     * 返回三级分类树形结构，一级分类包含子分类，子分类嵌套孙分类
     */
    @GetMapping(ProductServiceUris.ProductCategory.LIST_TREE)
    public R<List<CategoryVO>> list() {
        return R.ok(categoryService.listWithTree());
    }

    /**
     * 分页查询分类
     * 支持按名称模糊查询和关键字搜索
     */
    @PostMapping(ProductServiceUris.ProductCategory.PAGE)
    public R<PageData<CategoryVO>> page(@Valid CategoryQueryDTO query) {
        return R.ok(categoryService.page(query));
    }

    /**
     * 根据父ID查询子分类
     * 返回指定父分类下的直接子分类列表，按排序字段升序
     */
    @GetMapping(ProductServiceUris.ProductCategory.PARENT_BY_ID)
    public R<List<CategoryVO>> children(@PathVariable Long parentId) {
        return R.ok(categoryService.getChildrenByParentId(parentId));
    }

    /**
     * 批量删除分类
     * 接收分类ID列表，执行逻辑删除
     */
    @PostMapping(ProductServiceUris.ProductCategory.DELETE)
    public R<Void> delete(@RequestBody List<Long> ids) {
        categoryService.deleteBatch(ids);
        return R.ok();
    }

    /**
     * 修改分类信息
     * 更新分类的名称、图标、排序、显示状态等信息
     */
    @PostMapping(ProductServiceUris.ProductCategory.UPDATE)
    public R<Void> update(@Valid @RequestBody CategoryUpdateDTO dto) {
        categoryService.update(dto);
        return R.ok();
    }
}
