package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
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
    public R<PageData<SkuLadderVO>> page(SkuLadderQueryDTO query) {
        return R.ok(skuLadderService.page(query));
    }

    @GetMapping(CouponServiceUris.SkuLadder.INFO)
    public R<SkuLadderVO> info(@PathVariable Long id) {
        return R.ok(skuLadderService.getById(id));
    }

    @PostMapping(CouponServiceUris.SkuLadder.SAVE)
    public R<Integer> save(@RequestBody SkuLadderVO vo) {
        return R.ok(skuLadderService.save(vo));
    }

    @PostMapping(CouponServiceUris.SkuLadder.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SkuLadderVO> list) {
        return R.ok(skuLadderService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SkuLadder.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SkuLadderVO vo) {
        return R.ok(skuLadderService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SkuLadder.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(skuLadderService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SkuLadder.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(skuLadderService.removeByIds(ids));
    }
}
