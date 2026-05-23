package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.AttrQueryDTO;
import com.wheatmall.product.vo.AttrRespVO;
import com.wheatmall.product.vo.AttrVO;

import java.util.List;

/**
 * Attr Service
 */
public interface AttrService {

    PageData<AttrRespVO> page(AttrQueryDTO query);
    PageData<AttrRespVO> queryBaseAttrPage(AttrQueryDTO query, Long catelogId, String attrType);
    AttrRespVO getAttrInfo(Long id);
    AttrRespVO getVOById(Long id);
    Integer save(AttrVO vo);
    Integer saveAttr(AttrVO vo);
    Integer saveBatch(List<AttrVO> list);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
    void updateAttrById(Long id, AttrVO attr);

    List<AttrRespVO> getRelationAttr(Long attrgroupId);
    PageData<?> getNoRelationAttr(AttrQueryDTO query, Long attrgroupId);
}
