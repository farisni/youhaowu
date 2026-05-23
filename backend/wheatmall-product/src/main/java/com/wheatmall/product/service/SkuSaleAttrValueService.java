package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuSaleAttrValueVO;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SkuSaleAttrValueEntity;

/**
 * SkuSaleAttrValue Service
 */
public interface SkuSaleAttrValueService {

    /** 分页查询 */
    PageData<SkuSaleAttrValueVO> page(BaseQueryDTO query);

    /** 根据ID查询 */
    SkuSaleAttrValueEntity getById(Long id);

    /** 保存 */
    void save(SkuSaleAttrValueEntity entity);

    /** 批量保存 */
    void saveBatch(java.util.List<SkuSaleAttrValueEntity> list);

    /** 根据ID更新 */
    void updateById(SkuSaleAttrValueEntity entity);

    /** 批量删除 */
    void removeByIds(java.util.List<Long> ids);
}
