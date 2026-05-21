package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品三级分类控制器
 * 实现商品三级分类管理，支持树形结构获取、分类信息查询、修改以及逻辑删除功能
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/category/list/tree")
    public R<List<CategoryVO>> list(){
        List<CategoryVO> entities = categoryService.listWithTree();
        return R.ok(entities);
    }
}
