package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.SeckillSkuNoticeQueryDTO;
import com.wheatmall.coupon.service.SeckillSkuNoticeService;
import com.wheatmall.coupon.vo.SeckillSkuNoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SeckillSkuNotice 控制器
 */
@RestController
public class SeckillSkuNoticeController {

    @Autowired
    private SeckillSkuNoticeService seckillSkuNoticeService;

    @GetMapping(CouponServiceUris.SeckillSkuNotice.PAGE)
    public R<PageData<SeckillSkuNoticeVO>> page(SeckillSkuNoticeQueryDTO query) {
        return R.ok(seckillSkuNoticeService.page(query));
    }

    @GetMapping(CouponServiceUris.SeckillSkuNotice.INFO)
    public R<SeckillSkuNoticeVO> info(@PathVariable Long id) {
        return R.ok(seckillSkuNoticeService.getById(id));
    }

    @PostMapping(CouponServiceUris.SeckillSkuNotice.SAVE)
    public R<Integer> save(@RequestBody SeckillSkuNoticeVO vo) {
        return R.ok(seckillSkuNoticeService.save(vo));
    }

    @PostMapping(CouponServiceUris.SeckillSkuNotice.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SeckillSkuNoticeVO> list) {
        return R.ok(seckillSkuNoticeService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SeckillSkuNotice.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SeckillSkuNoticeVO vo) {
        return R.ok(seckillSkuNoticeService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuNotice.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(seckillSkuNoticeService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuNotice.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(seckillSkuNoticeService.removeByIds(ids));
    }
}
