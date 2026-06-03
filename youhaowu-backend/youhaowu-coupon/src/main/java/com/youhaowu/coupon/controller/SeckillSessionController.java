package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<SeckillSessionVO> page(SeckillSessionQueryDTO query) {
        return seckillSessionService.page(query);
    }

    @GetMapping(CouponServiceUris.SeckillSession.INFO)
    public SeckillSessionVO info(@PathVariable Long id) {
        return seckillSessionService.getById(id);
    }

    @PostMapping(CouponServiceUris.SeckillSession.SAVE)
    public Integer save(@RequestBody SeckillSessionVO vo) {
        return seckillSessionService.save(vo);
    }

    @PostMapping(CouponServiceUris.SeckillSession.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SeckillSessionVO> list) {
        return seckillSessionService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SeckillSession.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SeckillSessionVO vo) {
        return seckillSessionService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SeckillSession.DELETE)
    public Integer delete(@PathVariable Long id) {
        return seckillSessionService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SeckillSession.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return seckillSessionService.removeByIds(ids);
    }
}
