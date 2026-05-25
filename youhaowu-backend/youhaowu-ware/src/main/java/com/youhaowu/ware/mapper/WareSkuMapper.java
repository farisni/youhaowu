package com.youhaowu.ware.mapper;

import com.youhaowu.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Nidazhong
 * @email 535704264@qq.com
 * @date 2022-12-28 23:36:26
 */
@Mapper
public interface WareSkuMapper extends BaseMapper<WareSkuEntity> {

    void addStock(Long skuId, Long wareId, Integer skuNum);

    Long getSkuStock(Long item);
}
