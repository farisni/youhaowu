package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.coupon.dto.HomeSubjectSpuQueryDTO;
import com.youhaowu.coupon.service.HomeSubjectSpuService;
import com.youhaowu.coupon.vo.HomeSubjectSpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HomeSubjectSpu 控制器
 */
@RestController
public class HomeSubjectSpuController {

    @Autowired
    private HomeSubjectSpuService homeSubjectSpuService;

    @GetMapping(CouponServiceUris.HomeSubjectSpu.PAGE)
    public R<PageData<HomeSubjectSpuVO>> page(HomeSubjectSpuQueryDTO query) {
        return R.ok(homeSubjectSpuService.page(query));
    }

    @GetMapping(CouponServiceUris.HomeSubjectSpu.INFO)
    public R<HomeSubjectSpuVO> info(@PathVariable Long id) {
        return R.ok(homeSubjectSpuService.getById(id));
    }

    @PostMapping(CouponServiceUris.HomeSubjectSpu.SAVE)
    public R<Integer> save(@RequestBody HomeSubjectSpuVO vo) {
        return R.ok(homeSubjectSpuService.save(vo));
    }

    @PostMapping(CouponServiceUris.HomeSubjectSpu.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<HomeSubjectSpuVO> list) {
        return R.ok(homeSubjectSpuService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.HomeSubjectSpu.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody HomeSubjectSpuVO vo) {
        return R.ok(homeSubjectSpuService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.HomeSubjectSpu.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(homeSubjectSpuService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.HomeSubjectSpu.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(homeSubjectSpuService.removeByIds(ids));
    }
}
