package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SpuBoundsQueryDTO;
import com.wheatmall.coupon.vo.SpuBoundsVO;

import java.util.List;

/**
 * SpuBounds Service
 */
public interface SpuBoundsService {
    PageData<SpuBoundsVO> page(SpuBoundsQueryDTO query);
    SpuBoundsVO getById(Long id);
    Integer save(SpuBoundsVO vo);
    Integer saveBatch(List<SpuBoundsVO> list);
    Integer updateById(Long id, SpuBoundsVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
