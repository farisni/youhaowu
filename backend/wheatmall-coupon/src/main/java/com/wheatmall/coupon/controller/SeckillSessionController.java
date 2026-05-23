package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.SeckillSessionQueryDTO;
import com.wheatmall.coupon.service.SeckillSessionService;
import com.wheatmall.coupon.vo.SeckillSessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SeckillSession 控制器
 */
@RestController
public class SeckillSessionController {

    @Autowired
    private SeckillSessionService seckillSessionService;

    @GetMapping(CouponServiceUris.SeckillSession.PAGE)
    public R<PageData<SeckillSessionVO>> page(SeckillSessionQueryDTO query) {
        return R.ok(seckillSessionService.page(query));
    }

    @GetMapping(CouponServiceUris.SeckillSession.INFO)
    public R<SeckillSessionVO> info(@PathVariable Long id) {
        return R.ok(seckillSessionService.getById(id));
    }

    @PostMapping(CouponServiceUris.SeckillSession.SAVE)
    public R<Integer> save(@RequestBody SeckillSessionVO vo) {
        return R.ok(seckillSessionService.save(vo));
    }

    @PostMapping(CouponServiceUris.SeckillSession.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<SeckillSessionVO> list) {
        return R.ok(seckillSessionService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.SeckillSession.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody SeckillSessionVO vo) {
        return R.ok(seckillSessionService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.SeckillSession.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(seckillSessionService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.SeckillSession.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(seckillSessionService.removeByIds(ids));
    }
}
