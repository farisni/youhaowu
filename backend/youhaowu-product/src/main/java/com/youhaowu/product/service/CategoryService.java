package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.dto.CategoryQueryDTO;
import com.youhaowu.product.dto.CategoryUpdateDTO;
import com.youhaowu.product.vo.CategoryVO;

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

    /**
     * 根据父ID查询子分类
     */
    List<CategoryVO> getChildrenByParentId(Long parentId);

    /**
     * 批量删除分类
     */
    Integer deleteBatch(List<Long> ids);

    /**
     * 修改分类信息
     */
    Integer update(CategoryUpdateDTO dto);
}
