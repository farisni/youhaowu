package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.coupon.dto.HomeSubjectQueryDTO;
import com.youhaowu.coupon.service.HomeSubjectService;
import com.youhaowu.coupon.vo.HomeSubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HomeSubject 控制器
 */
@RestController
public class HomeSubjectController {

    @Autowired
    private HomeSubjectService homeSubjectService;

    @GetMapping(CouponServiceUris.HomeSubject.PAGE)
    public R<PageData<HomeSubjectVO>> page(HomeSubjectQueryDTO query) {
        return R.ok(homeSubjectService.page(query));
    }

    @GetMapping(CouponServiceUris.HomeSubject.INFO)
    public R<HomeSubjectVO> info(@PathVariable Long id) {
        return R.ok(homeSubjectService.getById(id));
    }

    @PostMapping(CouponServiceUris.HomeSubject.SAVE)
    public R<Integer> save(@RequestBody HomeSubjectVO vo) {
        return R.ok(homeSubjectService.save(vo));
    }

    @PostMapping(CouponServiceUris.HomeSubject.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<HomeSubjectVO> list) {
        return R.ok(homeSubjectService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.HomeSubject.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody HomeSubjectVO vo) {
        return R.ok(homeSubjectService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.HomeSubject.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(homeSubjectService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.HomeSubject.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(homeSubjectService.removeByIds(ids));
    }
}
