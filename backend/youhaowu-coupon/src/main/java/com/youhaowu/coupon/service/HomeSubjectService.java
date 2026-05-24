package com.youhaowu.coupon.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.HomeSubjectQueryDTO;
import com.youhaowu.coupon.vo.HomeSubjectVO;

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
