package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SkuLadderQueryDTO;
import com.youhaowu.coupon.service.SkuLadderService;
import com.youhaowu.coupon.vo.SkuLadderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SkuLadder 控制器
 */
@RestController
public class SkuLadderController {

    @Autowired
    private SkuLadderService skuLadderService;

    @GetMapping(CouponServiceUris.SkuLadder.PAGE)
    public PageData<SkuLadderVO> page(SkuLadderQueryDTO query) {
        return skuLadderService.page(query);
    }

    @GetMapping(CouponServiceUris.SkuLadder.INFO)
    public SkuLadderVO info(@PathVariable Long id) {
        return skuLadderService.getById(id);
    }

    @PostMapping(CouponServiceUris.SkuLadder.SAVE)
    public Integer save(@RequestBody SkuLadderVO vo) {
        return skuLadderService.save(vo);
    }

    @PostMapping(CouponServiceUris.SkuLadder.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SkuLadderVO> list) {
        return skuLadderService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SkuLadder.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SkuLadderVO vo) {
        return skuLadderService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SkuLadder.DELETE)
    public Integer delete(@PathVariable Long id) {
        return skuLadderService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SkuLadder.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return skuLadderService.removeByIds(ids);
    }
}
