package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.coupon.dto.HomeAdvQueryDTO;
import com.youhaowu.coupon.service.HomeAdvService;
import com.youhaowu.coupon.vo.HomeAdvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HomeAdv 控制器
 */
@RestController
public class HomeAdvController {

    @Autowired
    private HomeAdvService homeAdvService;

    @GetMapping(CouponServiceUris.HomeAdv.PAGE)
    public R<PageData<HomeAdvVO>> page(HomeAdvQueryDTO query) {
        return R.ok(homeAdvService.page(query));
    }

    @GetMapping(CouponServiceUris.HomeAdv.INFO)
    public R<HomeAdvVO> info(@PathVariable Long id) {
        return R.ok(homeAdvService.getById(id));
    }

    @PostMapping(CouponServiceUris.HomeAdv.SAVE)
    public R<Integer> save(@RequestBody HomeAdvVO vo) {
        return R.ok(homeAdvService.save(vo));
    }

    @PostMapping(CouponServiceUris.HomeAdv.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<HomeAdvVO> list) {
        return R.ok(homeAdvService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.HomeAdv.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody HomeAdvVO vo) {
        return R.ok(homeAdvService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.HomeAdv.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(homeAdvService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.HomeAdv.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(homeAdvService.removeByIds(ids));
    }
}
