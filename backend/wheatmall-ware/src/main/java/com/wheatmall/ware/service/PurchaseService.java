package com.wheatmall.ware.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.ware.dto.PurchaseQueryDTO;
import com.wheatmall.ware.vo.PurchaseVO;
import com.wheatmall.ware.vo.MergeVo;
import com.wheatmall.ware.vo.PurchaseDoneVo;

import java.util.List;

/**
 * Purchase Service
 */
public interface PurchaseService {

    PageData<PurchaseVO> queryPageUnreceive(PurchaseQueryDTO query);
    void mergePurchase(MergeVo mergeVo);
    void received(List<Long> ids);
    void done(PurchaseDoneVo doneVo);

    PageData<PurchaseVO> page(PurchaseQueryDTO query);
    PurchaseVO getById(Long id);
    Integer save(PurchaseVO vo);
    Integer saveBatch(List<PurchaseVO> list);
    Integer updateById(Long id, PurchaseVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);
}
