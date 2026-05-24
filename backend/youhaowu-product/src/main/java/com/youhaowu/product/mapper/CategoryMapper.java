package com.youhaowu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhaowu.product.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类 Mapper
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
}
