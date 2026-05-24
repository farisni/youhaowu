package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.coupon.dto.SeckillSessionQueryDTO;
import com.youhaowu.coupon.service.SeckillSessionService;
import com.youhaowu.coupon.vo.SeckillSessionVO;
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
