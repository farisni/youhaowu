package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.query.AttrGroupQueryDTO;
import com.youhaowu.product.vo.AttrGroupVO;
import com.youhaowu.product.vo.AttrGroupWithAttrsVO;

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
    Integer removeByIds(List<Long> ids);
    List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}
