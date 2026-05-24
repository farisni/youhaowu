package com.youhaowu.ware.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.WareSkuQueryDTO;
import com.youhaowu.ware.vo.WareSkuVO;
import com.youhaowu.ware.vo.MergeVo;
import com.youhaowu.ware.vo.PurchaseDoneVo;
import com.youhaowu.common.vo.SkuHasStockVO;

import java.util.List;

/**
 * WareSku Service
 */
public interface WareSkuService {

    List<SkuHasStockVO> getSkuHasStock(List<Long> skuIds);

    PageData<WareSkuVO> page(WareSkuQueryDTO query);
    WareSkuVO getById(Long id);
    Integer save(WareSkuVO vo);
    Integer saveBatch(List<WareSkuVO> list);
    Integer updateById(Long id, WareSkuVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
