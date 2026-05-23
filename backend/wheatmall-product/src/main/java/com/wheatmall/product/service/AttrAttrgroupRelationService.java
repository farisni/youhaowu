package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.AttrAttrgroupRelationVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.AttrAttrgroupRelationEntity;
import com.wheatmall.product.vo.AttrGroupRelationVO;
import java.util.List;

/**
 * AttrAttrgroupRelation Service
 */
public interface AttrAttrgroupRelationService {

    /** 分页查询 */
    PageData<AttrAttrgroupRelationVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    AttrAttrgroupRelationEntity getById(Long id);

    /** 保存 */
    Integer save(AttrAttrgroupRelationEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<AttrAttrgroupRelationEntity> list);

    /** 根据ID更新 */
    Integer updateById(AttrAttrgroupRelationEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
    void saveRelationBatch(List<AttrGroupRelationVO> vos);
}
