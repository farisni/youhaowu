package com.wheatmall.coupon.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.dto.HomeSubjectQueryDTO;
import com.wheatmall.coupon.vo.HomeSubjectVO;

import java.util.List;

/**
 * HomeSubject Service
 */
public interface HomeSubjectService {
    PageData<HomeSubjectVO> page(HomeSubjectQueryDTO query);
    HomeSubjectVO getById(Long id);
    Integer save(HomeSubjectVO vo);
    Integer saveBatch(List<HomeSubjectVO> list);
    Integer updateById(Long id, HomeSubjectVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
