package com.wheatmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wheatmall.product.entity.SpuInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息 Mapper
 */
@Mapper
public interface SpuInfoMapper extends BaseMapper<SpuInfoEntity> {

    /**
     * 更新SPU状态
     */
    void updaSpuStatus(@Param("spuId") Long spuId, @Param("code") Integer code);
}
