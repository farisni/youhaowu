package com.wheatmall.ware.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.query.PurchaseDetailQueryDTO;
import com.wheatmall.ware.vo.PurchaseDetailVO;

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
