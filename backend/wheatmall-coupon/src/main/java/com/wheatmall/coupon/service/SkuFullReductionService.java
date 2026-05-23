package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SkuFullReductionQueryDTO;
import com.wheatmall.coupon.vo.SkuFullReductionVO;

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
