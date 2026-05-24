package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.vo.CategoryBrandRelationVO;

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
    Integer removeByIds(java.util.List<Long> ids);    Integer saveDetail(CategoryBrandRelationVO vo);
    Integer updateBrand(Long brandId, String name);
    Integer updateCategory(Long catId, String name);
}
