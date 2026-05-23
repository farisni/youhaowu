package com.wheatmall.coupon.controller;

import com.wheatmall.common.constant.CouponServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.coupon.dto.HomeAdvQueryDTO;
import com.wheatmall.coupon.service.HomeAdvService;
import com.wheatmall.coupon.vo.HomeAdvVO;
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
