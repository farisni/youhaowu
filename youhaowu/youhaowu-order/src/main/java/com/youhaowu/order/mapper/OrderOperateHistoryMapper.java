package com.youhaowu.order.mapper;

import com.youhaowu.order.entity.OrderOperateHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author Nidazhong
 * @email 535704264@qq.com
 * @date 2022-12-28 23:32:42
 */
@Mapper
public interface OrderOperateHistoryMapper extends BaseMapper<OrderOperateHistoryEntity> {
	
}
