package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuImagesVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SpuImagesEntity;
import java.util.List;

/**
 * SpuImages Service
 */
public interface SpuImagesService {

    /** 分页查询 */
    PageData<SpuImagesVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SpuImagesEntity getById(Long id);

    /** 保存 */
    Integer save(SpuImagesEntity entity);

    /** 批量保存 */
    void saveBatch(List<SpuImagesEntity> list);

    /** 根据ID更新 */
    Integer updateById(SpuImagesEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
    void saveImages(Long spuId, List<String> images);
}
