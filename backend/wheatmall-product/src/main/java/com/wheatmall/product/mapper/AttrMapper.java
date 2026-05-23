package com.wheatmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wheatmall.product.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性 Mapper
 */
@Mapper
public interface AttrMapper extends BaseMapper<AttrEntity> {

    /**
     * 查询允许被检索的属性ID集合
     */
    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
