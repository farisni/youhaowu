package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.AttrGroupQueryDTO;
import com.wheatmall.product.vo.AttrGroupVO;
import com.wheatmall.product.vo.AttrGroupWithAttrsVO;

import java.util.List;

/**
 * AttrGroup Service
 */
public interface AttrGroupService {

    PageData<AttrGroupVO> page(AttrGroupQueryDTO query, Long catelogId);
    AttrGroupVO getById(Long id);
    AttrGroupVO getVOById(Long id);
    Integer save(AttrGroupVO vo);
    Integer saveBatch(List<AttrGroupVO> list);
    Integer updateById(Long id, AttrGroupVO vo);
    Integer removeById(Long id);
    void removeByIds(List<Long> ids);
    List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}
