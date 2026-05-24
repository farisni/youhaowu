package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.RefundInfoQueryDTO;
import com.youhaowu.order.vo.RefundInfoVO;

import java.util.List;

/**
 * RefundInfo Service
 */
public interface RefundInfoService {

    PageData<RefundInfoVO> page(RefundInfoQueryDTO query);
    RefundInfoVO getById(Long id);
    Integer save(RefundInfoVO vo);
    Integer saveBatch(List<RefundInfoVO> list);
    Integer updateById(Long id, RefundInfoVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
