package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderQueryDTO;
import com.youhaowu.order.vo.OrderVO;

import java.util.List;

/**
 * Order Service
 */
public interface OrderService {

    PageData<OrderVO> page(OrderQueryDTO query);
    OrderVO getById(Long id);
    Integer save(OrderVO vo);
    Integer saveBatch(List<OrderVO> list);
    Integer updateById(Long id, OrderVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
