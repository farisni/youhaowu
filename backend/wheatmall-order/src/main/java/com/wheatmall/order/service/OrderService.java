package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.query.OrderQueryDTO;
import com.wheatmall.order.vo.OrderVO;

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
