package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.dto.OrderOperateHistoryQueryDTO;
import com.wheatmall.order.vo.OrderOperateHistoryVO;

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
