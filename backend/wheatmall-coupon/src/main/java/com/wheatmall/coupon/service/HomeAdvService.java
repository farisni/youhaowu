package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.HomeAdvQueryDTO;
import com.wheatmall.coupon.vo.HomeAdvVO;

import java.util.List;

/**
 * HomeAdv Service
 */
public interface HomeAdvService {
    PageData<HomeAdvVO> page(HomeAdvQueryDTO query);
    HomeAdvVO getById(Long id);
    Integer save(HomeAdvVO vo);
    Integer saveBatch(List<HomeAdvVO> list);
    Integer updateById(Long id, HomeAdvVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
