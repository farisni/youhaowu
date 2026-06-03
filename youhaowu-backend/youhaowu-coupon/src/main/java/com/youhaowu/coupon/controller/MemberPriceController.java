package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.dto.MemberPriceQueryDTO;
import com.youhaowu.coupon.service.MemberPriceService;
import com.youhaowu.coupon.vo.MemberPriceVO;
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
    public PageData<MemberPriceVO> page(MemberPriceQueryDTO query) {
        return memberPriceService.page(query);
    }

    @GetMapping(CouponServiceUris.MemberPrice.INFO)
    public MemberPriceVO info(@PathVariable Long id) {
        return memberPriceService.getById(id);
    }

    @PostMapping(CouponServiceUris.MemberPrice.SAVE)
    public Integer save(@RequestBody MemberPriceVO vo) {
        return memberPriceService.save(vo);
    }

    @PostMapping(CouponServiceUris.MemberPrice.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberPriceVO> list) {
        return memberPriceService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.MemberPrice.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberPriceVO vo) {
        return memberPriceService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.MemberPrice.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberPriceService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.MemberPrice.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberPriceService.removeByIds(ids);
    }
}
