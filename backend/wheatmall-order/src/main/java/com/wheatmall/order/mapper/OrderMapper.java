package com.wheatmall.order.mapper;

import com.wheatmall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Nidazhong
 * @email 535704264@qq.com
 * @date 2022-12-28 23:32:42
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
	
}
