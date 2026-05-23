package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.dto.PaymentInfoQueryDTO;
import com.wheatmall.order.vo.PaymentInfoVO;

import java.util.List;

/**
 * PaymentInfo Service
 */
public interface PaymentInfoService {

    PageData<PaymentInfoVO> page(PaymentInfoQueryDTO query);
    PaymentInfoVO getById(Long id);
    Integer save(PaymentInfoVO vo);
    Integer saveBatch(List<PaymentInfoVO> list);
    Integer updateById(Long id, PaymentInfoVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
