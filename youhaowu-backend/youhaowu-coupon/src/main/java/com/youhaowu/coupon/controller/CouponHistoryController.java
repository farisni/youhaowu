package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<CouponHistoryVO> page(CouponHistoryQueryDTO query) {
        return couponHistoryService.page(query);
    }

    @GetMapping(CouponServiceUris.CouponHistory.INFO)
    public CouponHistoryVO info(@PathVariable Long id) {
        return couponHistoryService.getById(id);
    }

    @PostMapping(CouponServiceUris.CouponHistory.SAVE)
    public Integer save(@RequestBody CouponHistoryVO vo) {
        return couponHistoryService.save(vo);
    }

    @PostMapping(CouponServiceUris.CouponHistory.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<CouponHistoryVO> list) {
        return couponHistoryService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.CouponHistory.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody CouponHistoryVO vo) {
        return couponHistoryService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.CouponHistory.DELETE)
    public Integer delete(@PathVariable Long id) {
        return couponHistoryService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.CouponHistory.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return couponHistoryService.removeByIds(ids);
    }
}
