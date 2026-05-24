package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.coupon.dto.CouponHistoryQueryDTO;
import com.youhaowu.coupon.service.CouponHistoryService;
import com.youhaowu.coupon.vo.CouponHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CouponHistory 控制器
 */
@RestController
public class CouponHistoryController {

    @Autowired
    private CouponHistoryService couponHistoryService;

    @GetMapping(CouponServiceUris.CouponHistory.PAGE)
    public R<PageData<CouponHistoryVO>> page(CouponHistoryQueryDTO query) {
        return R.ok(couponHistoryService.page(query));
    }

    @GetMapping(CouponServiceUris.CouponHistory.INFO)
    public R<CouponHistoryVO> info(@PathVariable Long id) {
        return R.ok(couponHistoryService.getById(id));
    }

    @PostMapping(CouponServiceUris.CouponHistory.SAVE)
    public R<Integer> save(@RequestBody CouponHistoryVO vo) {
        return R.ok(couponHistoryService.save(vo));
    }

    @PostMapping(CouponServiceUris.CouponHistory.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<CouponHistoryVO> list) {
        return R.ok(couponHistoryService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.CouponHistory.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody CouponHistoryVO vo) {
        return R.ok(couponHistoryService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.CouponHistory.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(couponHistoryService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.CouponHistory.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(couponHistoryService.removeByIds(ids));
    }
}
