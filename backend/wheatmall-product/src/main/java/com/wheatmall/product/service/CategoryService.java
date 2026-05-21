package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.dto.CategoryQueryDTO;
import com.wheatmall.product.vo.CategoryVO;

import java.util.List;

/**
 * 商品分类 Service
 */
public interface CategoryService {

    /**
     * 获取分类树
     */
    List<CategoryVO> listWithTree();

    /**
     * 分页查询分类
     */
    PageData<CategoryVO> page(CategoryQueryDTO query);
}
