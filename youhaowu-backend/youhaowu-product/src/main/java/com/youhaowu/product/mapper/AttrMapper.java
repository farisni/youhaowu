package com.youhaowu.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhaowu.product.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品属性 Mapper
 */
@Mapper
public interface AttrMapper extends BaseMapper<AttrEntity> {

    /**
     * 查询允许被检索的属性ID集合
     */
    @Select("<script>SELECT attr_id FROM pms_attr WHERE attr_id IN " +
            "<foreach collection='attrIds' item='id' open='(' separator=',' close=')'>#{id}</foreach>" +
            " AND search_type = 1</script>")
    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
