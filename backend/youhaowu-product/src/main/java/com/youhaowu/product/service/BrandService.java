package com.youhaowu.product.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.query.BrandQueryDTO;
import com.youhaowu.product.vo.BrandVO;
import java.util.List;

/**
 * Brand Service
 */
public interface BrandService {

    PageData<BrandVO> page(BrandQueryDTO query);
    BrandVO getById(Long id);
    BrandVO getVOById(Long id);
    Integer save(BrandVO vo);
    Integer saveBatch(List<BrandVO> list);
    Integer updateById(Long id, BrandVO vo);
    Integer removeById(Long id);
    void updateDetail(Long id, BrandVO vo);
}
