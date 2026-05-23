package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.MemberPriceQueryDTO;
import com.wheatmall.coupon.service.MemberPriceService;
import com.wheatmall.coupon.vo.MemberPriceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MemberPrice 控制器
 */
@RestController
public class MemberPriceController {

    @Autowired
    private MemberPriceService memberPriceService;

    @GetMapping(CouponServiceUris.MemberPrice.PAGE)
    public R<PageData<MemberPriceVO>> page(MemberPriceQueryDTO query) {
        return R.ok(memberPriceService.page(query));
    }

    @GetMapping(CouponServiceUris.MemberPrice.INFO)
    public R<MemberPriceVO> info(@PathVariable Long id) {
        return R.ok(memberPriceService.getById(id));
    }

    @PostMapping(CouponServiceUris.MemberPrice.SAVE)
    public R<Integer> save(@RequestBody MemberPriceVO vo) {
        return R.ok(memberPriceService.save(vo));
    }

    @PostMapping(CouponServiceUris.MemberPrice.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberPriceVO> list) {
        return R.ok(memberPriceService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.MemberPrice.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberPriceVO vo) {
        return R.ok(memberPriceService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.MemberPrice.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberPriceService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.MemberPrice.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberPriceService.removeByIds(ids));
    }
}
