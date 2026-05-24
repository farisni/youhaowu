package com.youhaowu.ware.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.PurchaseQueryDTO;
import com.youhaowu.ware.vo.PurchaseVO;
import com.youhaowu.ware.vo.MergeVo;
import com.youhaowu.ware.vo.PurchaseDoneVo;

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
