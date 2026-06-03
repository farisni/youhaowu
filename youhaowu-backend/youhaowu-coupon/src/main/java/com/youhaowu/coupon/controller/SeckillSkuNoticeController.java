package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.SeckillSkuNoticeQueryDTO;
import com.youhaowu.coupon.service.SeckillSkuNoticeService;
import com.youhaowu.coupon.vo.SeckillSkuNoticeVO;
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
    public PageData<SeckillSkuNoticeVO> page(SeckillSkuNoticeQueryDTO query) {
        return seckillSkuNoticeService.page(query);
    }

    @GetMapping(CouponServiceUris.SeckillSkuNotice.INFO)
    public SeckillSkuNoticeVO info(@PathVariable Long id) {
        return seckillSkuNoticeService.getById(id);
    }

    @PostMapping(CouponServiceUris.SeckillSkuNotice.SAVE)
    public Integer save(@RequestBody SeckillSkuNoticeVO vo) {
        return seckillSkuNoticeService.save(vo);
    }

    @PostMapping(CouponServiceUris.SeckillSkuNotice.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<SeckillSkuNoticeVO> list) {
        return seckillSkuNoticeService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.SeckillSkuNotice.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody SeckillSkuNoticeVO vo) {
        return seckillSkuNoticeService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuNotice.DELETE)
    public Integer delete(@PathVariable Long id) {
        return seckillSkuNoticeService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.SeckillSkuNotice.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return seckillSkuNoticeService.removeByIds(ids);
    }
}
