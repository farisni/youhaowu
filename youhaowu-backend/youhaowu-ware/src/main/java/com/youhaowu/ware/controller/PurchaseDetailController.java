package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.PurchaseDetailQueryDTO;
import com.youhaowu.ware.service.PurchaseDetailService;
import com.youhaowu.ware.vo.PurchaseDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PurchaseDetail 控制器
 */
@RestController
public class PurchaseDetailController {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @GetMapping(WareServiceUris.PurchaseDetail.PAGE)
    public PageData<PurchaseDetailVO> list(PurchaseDetailQueryDTO query) {
        return purchaseDetailService.page(query);
    }

    @GetMapping(WareServiceUris.PurchaseDetail.INFO)
    public PurchaseDetailVO info(@PathVariable Long id) {
        return purchaseDetailService.getById(id);
    }

    @GetMapping(WareServiceUris.PurchaseDetail.SAVE)
    public Integer save(@RequestBody PurchaseDetailVO vo) {
        return purchaseDetailService.save(vo);
    }

    @GetMapping(WareServiceUris.PurchaseDetail.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody PurchaseDetailVO vo) {
        return purchaseDetailService.updateById(id, vo);
    }

    @GetMapping(WareServiceUris.PurchaseDetail.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return purchaseDetailService.removeByIds(ids);
    }
}
