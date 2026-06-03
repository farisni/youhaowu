package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<HomeSubjectVO> page(HomeSubjectQueryDTO query) {
        return homeSubjectService.page(query);
    }

    @GetMapping(CouponServiceUris.HomeSubject.INFO)
    public HomeSubjectVO info(@PathVariable Long id) {
        return homeSubjectService.getById(id);
    }

    @PostMapping(CouponServiceUris.HomeSubject.SAVE)
    public Integer save(@RequestBody HomeSubjectVO vo) {
        return homeSubjectService.save(vo);
    }

    @PostMapping(CouponServiceUris.HomeSubject.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<HomeSubjectVO> list) {
        return homeSubjectService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.HomeSubject.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody HomeSubjectVO vo) {
        return homeSubjectService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.HomeSubject.DELETE)
    public Integer delete(@PathVariable Long id) {
        return homeSubjectService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.HomeSubject.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return homeSubjectService.removeByIds(ids);
    }
}
