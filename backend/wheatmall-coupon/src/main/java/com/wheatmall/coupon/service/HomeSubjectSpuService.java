package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.HomeSubjectSpuQueryDTO;
import com.wheatmall.coupon.vo.HomeSubjectSpuVO;

import java.util.List;

/**
 * HomeSubjectSpu Service
 */
public interface HomeSubjectSpuService {
    PageData<HomeSubjectSpuVO> page(HomeSubjectSpuQueryDTO query);
    HomeSubjectSpuVO getById(Long id);
    Integer save(HomeSubjectSpuVO vo);
    Integer saveBatch(List<HomeSubjectSpuVO> list);
    Integer updateById(Long id, HomeSubjectSpuVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
