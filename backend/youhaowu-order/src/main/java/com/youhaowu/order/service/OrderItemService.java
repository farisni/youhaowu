package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderItemQueryDTO;
import com.youhaowu.order.vo.OrderItemVO;

import java.util.List;

/**
 * OrderItem Service
 */
public interface OrderItemService {

    PageData<OrderItemVO> page(OrderItemQueryDTO query);
    OrderItemVO getById(Long id);
    Integer save(OrderItemVO vo);
    Integer saveBatch(List<OrderItemVO> list);
    Integer updateById(Long id, OrderItemVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
