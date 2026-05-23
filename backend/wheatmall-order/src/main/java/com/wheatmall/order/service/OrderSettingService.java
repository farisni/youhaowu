package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.dto.OrderSettingQueryDTO;
import com.wheatmall.order.vo.OrderSettingVO;

import java.util.List;

/**
 * OrderSetting Service
 */
public interface OrderSettingService {

    PageData<OrderSettingVO> page(OrderSettingQueryDTO query);
    OrderSettingVO getById(Long id);
    Integer save(OrderSettingVO vo);
    Integer saveBatch(List<OrderSettingVO> list);
    Integer updateById(Long id, OrderSettingVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
