package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderOperateHistoryQueryDTO;
import com.youhaowu.order.vo.OrderOperateHistoryVO;

import java.util.List;

/**
 * OrderOperateHistory Service
 */
public interface OrderOperateHistoryService {

    PageData<OrderOperateHistoryVO> page(OrderOperateHistoryQueryDTO query);
    OrderOperateHistoryVO getById(Long id);
    Integer save(OrderOperateHistoryVO vo);
    Integer saveBatch(List<OrderOperateHistoryVO> list);
    Integer updateById(Long id, OrderOperateHistoryVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
