package com.wheatmall.order.mapper;

import com.wheatmall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author Nidazhong
 * @email 535704264@qq.com
 * @date 2022-12-28 23:32:42
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItemEntity> {
	
}
