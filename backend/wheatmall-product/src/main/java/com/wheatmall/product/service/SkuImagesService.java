package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuImagesVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SkuImagesEntity;

/**
 * SkuImages Service
 */
public interface SkuImagesService {

    /** 分页查询 */
    PageData<SkuImagesVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SkuImagesEntity getById(Long id);

    /** 保存 */
    void save(SkuImagesEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<SkuImagesEntity> list);

    /** 根据ID更新 */
    void updateById(SkuImagesEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
}
