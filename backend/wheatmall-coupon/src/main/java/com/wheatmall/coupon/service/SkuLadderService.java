package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.SkuLadderQueryDTO;
import com.wheatmall.coupon.vo.SkuLadderVO;

import java.util.List;

/**
 * SkuLadder Service
 */
public interface SkuLadderService {
    PageData<SkuLadderVO> page(SkuLadderQueryDTO query);
    SkuLadderVO getById(Long id);
    Integer save(SkuLadderVO vo);
    Integer saveBatch(List<SkuLadderVO> list);
    Integer updateById(Long id, SkuLadderVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
