package com.youhaowu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhaowu.product.entity.AttrAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联 Mapper
 */
@Mapper
public interface AttrAttrgroupRelationMapper extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 批量删除关联
     */
    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
