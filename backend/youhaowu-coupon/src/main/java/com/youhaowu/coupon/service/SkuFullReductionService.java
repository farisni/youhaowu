package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SkuFullReductionQueryDTO;
import com.youhaowu.coupon.vo.SkuFullReductionVO;

import java.util.List;

/**
 * SkuFullReduction Service
 */
public interface SkuFullReductionService {
    PageData<SkuFullReductionVO> page(SkuFullReductionQueryDTO query);
    SkuFullReductionVO getById(Long id);
    Integer save(SkuFullReductionVO vo);
    Integer saveBatch(List<SkuFullReductionVO> list);
    Integer updateById(Long id, SkuFullReductionVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
