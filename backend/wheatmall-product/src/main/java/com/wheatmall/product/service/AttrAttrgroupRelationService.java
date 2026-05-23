package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.AttrAttrgroupRelationVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.vo.AttrGroupRelationVO;
import java.util.List;

/**
 * AttrAttrgroupRelation Service
 */
public interface AttrAttrgroupRelationService {

    /** 分页查询 */
    PageData<AttrAttrgroupRelationVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    AttrAttrgroupRelationVO getById(Long id);

    /** 保存 */
    Integer save(AttrAttrgroupRelationVO vo);

    /** 批量保存 */
    Integer saveBatch(java.util.List<AttrAttrgroupRelationVO> list);

    /** 根据ID更新 */
    Integer updateById(AttrAttrgroupRelationVO vo);

    /** 批量删除 */
    Integer removeByIds(java.util.List<Long> ids);
    Integer saveRelationBatch(List<AttrGroupRelationVO> vos);
}
