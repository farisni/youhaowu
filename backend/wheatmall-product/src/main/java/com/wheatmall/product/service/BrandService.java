package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.BrandQueryDTO;
import com.wheatmall.product.vo.BrandVo;

/**
 * Brand Service
 */
public interface BrandService {

    PageData<BrandVo> page(BrandQueryDTO query);
    BrandVo getById(Long id);
    BrandVo getVOById(Long id);
    void save(BrandVo vo);
    void saveBatch(java.util.List<BrandVo> list);
    void updateById(Long id, BrandVo vo);
    void removeById(Long id);
    void updateDetail(Long id, BrandVo vo);
}
