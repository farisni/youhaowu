package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.vo.CategoryBrandRelationVO;

/**
 * CategoryBrandRelation Service
 */
public interface CategoryBrandRelationService {

    PageData<CategoryBrandRelationVO> page(BaseQueryDTO query);
    CategoryBrandRelationVO getById(Long id);
    CategoryBrandRelationVO getVOById(Long id);
    Integer save(CategoryBrandRelationVO vo);
    Integer saveBatch(java.util.List<CategoryBrandRelationVO> list);
    Integer updateById(CategoryBrandRelationVO vo);
    void removeByIds(java.util.List<Long> ids);    void saveDetail(CategoryBrandRelationVO vo);
    void updateBrand(Long brandId, String name);
    void updateCategory(Long catId, String name);
}
