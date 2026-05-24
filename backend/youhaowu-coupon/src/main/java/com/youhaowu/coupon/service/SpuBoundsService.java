package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SpuBoundsQueryDTO;
import com.youhaowu.coupon.vo.SpuBoundsVO;

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
