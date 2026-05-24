package com.youhaowu.ware.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.PurchaseDetailQueryDTO;
import com.youhaowu.ware.vo.PurchaseDetailVO;

import java.util.List;

/**
 * PurchaseDetail Service
 */
public interface PurchaseDetailService {

    PageData<PurchaseDetailVO> page(PurchaseDetailQueryDTO query);
    PurchaseDetailVO getById(Long id);
    Integer save(PurchaseDetailVO vo);
    Integer saveBatch(List<PurchaseDetailVO> list);
    Integer updateById(Long id, PurchaseDetailVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
