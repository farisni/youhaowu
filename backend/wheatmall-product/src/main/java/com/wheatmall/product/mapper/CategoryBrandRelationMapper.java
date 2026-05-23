package com.wheatmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wheatmall.product.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联 Mapper
 */
@Mapper
public interface CategoryBrandRelationMapper extends BaseMapper<CategoryBrandRelationEntity> {

    /**
     * 更新分类名称
     */
    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
