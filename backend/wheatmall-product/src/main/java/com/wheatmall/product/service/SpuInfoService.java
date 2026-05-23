package com.wheatmall.product.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.vo.SpuInfoVO;
import com.wheatmall.product.vo.SpuSaveVo;
import com.wheatmall.product.entity.SpuInfoEntity;

/**
 * spu信息 Service
 */
public interface SpuInfoService {

    PageData<SpuInfoVO> page(BaseQueryDTO query);
    SpuInfoVO getVOById(Long id);
    void saveBaseSpuInfo(SpuInfoEntity entity);
    void saveSupInfo(SpuSaveVo vo);
    void up(Long spuId);
}
