package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.query.BrandQueryDTO;
import com.wheatmall.product.vo.BrandVO;

/**
 * Brand Service
 */
public interface BrandService {

    PageData<BrandVO> page(BrandQueryDTO query);
    BrandVO getById(Long id);
    BrandVO getVOById(Long id);
    Integer save(BrandVO vo);
    void saveBatch(java.util.List<BrandVO> list);
    Integer updateById(Long id, BrandVO vo);
    Integer removeById(Long id);
    void updateDetail(Long id, BrandVO vo);
}
