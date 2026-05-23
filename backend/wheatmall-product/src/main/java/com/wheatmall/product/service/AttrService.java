package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.AttrQueryDTO;
import com.wheatmall.product.vo.AttrGroupRelationVo;
import com.wheatmall.product.vo.AttrRespVo;
import com.wheatmall.product.vo.AttrVo;

import java.util.List;

/**
 * Attr Service
 */
public interface AttrService {

    PageData<AttrRespVo> page(AttrQueryDTO query);
    PageData<AttrRespVo> queryBaseAttrPage(AttrQueryDTO query, Long catelogId, String attrType);
    AttrRespVo getAttrInfo(Long id);
    AttrRespVo getVOById(Long id);
    void save(AttrVo vo);
    void saveAttr(AttrVo vo);
    void saveBatch(List<AttrVo> list);
    void removeById(Long id);
    void removeByIds(List<Long> ids);
    void updateAttrById(Long id, AttrVo attr);

    List<AttrRespVo> getRelationAttr(Long attrgroupId);
    PageData<?> getNoRelationAttr(AttrQueryDTO query, Long attrgroupId);
}
