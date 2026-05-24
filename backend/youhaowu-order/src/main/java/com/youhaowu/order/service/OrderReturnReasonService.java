package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderReturnReasonQueryDTO;
import com.youhaowu.order.vo.OrderReturnReasonVO;

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
