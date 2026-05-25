package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.OrderSettingQueryDTO;
import com.youhaowu.order.vo.OrderSettingVO;

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
