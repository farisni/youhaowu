package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.dto.OrderReturnReasonQueryDTO;
import com.wheatmall.order.vo.OrderReturnReasonVO;

import java.util.List;

/**
 * OrderReturnReason Service
 */
public interface OrderReturnReasonService {

    PageData<OrderReturnReasonVO> page(OrderReturnReasonQueryDTO query);
    OrderReturnReasonVO getById(Long id);
    Integer save(OrderReturnReasonVO vo);
    Integer saveBatch(List<OrderReturnReasonVO> list);
    Integer updateById(Long id, OrderReturnReasonVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
