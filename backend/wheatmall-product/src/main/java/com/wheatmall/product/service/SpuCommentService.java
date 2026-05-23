package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuCommentVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SpuCommentEntity;

/**
 * SpuComment Service
 */
public interface SpuCommentService {

    /** 分页查询 */
    PageData<SpuCommentVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SpuCommentEntity getById(Long id);

    /** 保存 */
    void save(SpuCommentEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<SpuCommentEntity> list);

    /** 根据ID更新 */
    void updateById(SpuCommentEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
}
