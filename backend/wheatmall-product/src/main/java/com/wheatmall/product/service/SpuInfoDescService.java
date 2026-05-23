package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuInfoDescVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SpuInfoDescEntity;

/**
 * SpuInfoDesc Service
 */
public interface SpuInfoDescService {

    /** 分页查询 */
    PageData<SpuInfoDescVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SpuInfoDescEntity getById(Long id);

    /** 保存 */
    Integer save(SpuInfoDescEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<SpuInfoDescEntity> list);

    /** 根据ID更新 */
    Integer updateById(SpuInfoDescEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
    void saveSpuInfoDesc(SpuInfoDescEntity descEntity);
}
