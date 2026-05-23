package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.AttrAttrgroupRelationVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.AttrAttrgroupRelationEntity;
import com.wheatmall.product.vo.AttrGroupRelationVo;
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
    void save(AttrAttrgroupRelationEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<AttrAttrgroupRelationEntity> list);

    /** 根据ID更新 */
    void updateById(AttrAttrgroupRelationEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
    void saveRelationBatch(List<AttrGroupRelationVo> vos);
}
