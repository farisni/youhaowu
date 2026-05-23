package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.AttrGroupQueryDTO;
import com.wheatmall.product.vo.AttrGroupVO;
import com.wheatmall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;

/**
 * AttrGroup Service
 */
public interface AttrGroupService {

    PageData<AttrGroupVO> page(AttrGroupQueryDTO query, Long catelogId);
    AttrGroupVO getById(Long id);
    AttrGroupVO getVOById(Long id);
    void save(AttrGroupVO vo);
    void saveBatch(List<AttrGroupVO> list);
    void updateById(Long id, AttrGroupVO vo);
    void removeById(Long id);
    void removeByIds(List<Long> ids);
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}
