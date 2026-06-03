package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.PurchaseQueryDTO;
import com.youhaowu.ware.service.PurchaseService;
import com.youhaowu.ware.vo.MergeVo;
import com.youhaowu.ware.vo.PurchaseDoneVo;
import com.youhaowu.ware.vo.PurchaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购单控制器
 */
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 分页查询
     */
    @GetMapping(WareServiceUris.Purchase.PAGE)
    public PageData<PurchaseVO> list(PurchaseQueryDTO query) {
        return purchaseService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(WareServiceUris.Purchase.INFO)
    public PurchaseVO info(@PathVariable Long id) {
        return purchaseService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(WareServiceUris.Purchase.SAVE)
    public Integer save(@RequestBody PurchaseVO vo) {
        return purchaseService.save(vo);
    }

    /**
     * 更新
     */
    @PostMapping(WareServiceUris.Purchase.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody PurchaseVO vo) {
        return purchaseService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @PostMapping(WareServiceUris.Purchase.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return purchaseService.removeByIds(ids);
    }

    /**
     * 查询未领取的采购单
     */
    @GetMapping(WareServiceUris.Purchase.UNRECEIVE_LIST)
    public PageData<PurchaseVO> unreceiveList(PurchaseQueryDTO query) {
        return purchaseService.queryPageUnreceive(query);
    }

    /**
     * 合并整单
     */
    @PostMapping(WareServiceUris.Purchase.MERGE)
    public Object merge(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchase(mergeVo);
        
        return null;
    }

    /**
     * 领取采购单
     */
    @PostMapping(WareServiceUris.Purchase.RECEIVED)
    public Object received(@RequestBody List<Long> ids) {
        purchaseService.received(ids);
        
        return null;
    }

    /**
     * 完成采购单
     */
    @PostMapping(WareServiceUris.Purchase.DONE)
    public Object done(@RequestBody PurchaseDoneVo doneVo) {
        purchaseService.done(doneVo);
        
        return null;
    }
}
