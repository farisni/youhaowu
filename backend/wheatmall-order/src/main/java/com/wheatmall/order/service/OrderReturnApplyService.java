package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.query.OrderReturnApplyQueryDTO;
import com.wheatmall.order.vo.OrderReturnApplyVO;

import java.util.List;

/**
 * OrderReturnApply Service
 */
public interface OrderReturnApplyService {

    PageData<OrderReturnApplyVO> page(OrderReturnApplyQueryDTO query);
    OrderReturnApplyVO getById(Long id);
    Integer save(OrderReturnApplyVO vo);
    Integer saveBatch(List<OrderReturnApplyVO> list);
    Integer updateById(Long id, OrderReturnApplyVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
