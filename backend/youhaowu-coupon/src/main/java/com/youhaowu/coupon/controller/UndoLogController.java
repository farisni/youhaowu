package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.coupon.dto.UndoLogQueryDTO;
import com.youhaowu.coupon.service.UndoLogService;
import com.youhaowu.coupon.vo.UndoLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UndoLog 控制器
 */
@RestController
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;

    @GetMapping(CouponServiceUris.UndoLog.PAGE)
    public R<PageData<UndoLogVO>> page(UndoLogQueryDTO query) {
        return R.ok(undoLogService.page(query));
    }

    @GetMapping(CouponServiceUris.UndoLog.INFO)
    public R<UndoLogVO> info(@PathVariable Long id) {
        return R.ok(undoLogService.getById(id));
    }

    @PostMapping(CouponServiceUris.UndoLog.SAVE)
    public R<Integer> save(@RequestBody UndoLogVO vo) {
        return R.ok(undoLogService.save(vo));
    }

    @PostMapping(CouponServiceUris.UndoLog.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<UndoLogVO> list) {
        return R.ok(undoLogService.saveBatch(list));
    }

    @PutMapping(CouponServiceUris.UndoLog.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody UndoLogVO vo) {
        return R.ok(undoLogService.updateById(id, vo));
    }

    @DeleteMapping(CouponServiceUris.UndoLog.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(undoLogService.removeById(id));
    }

    @DeleteMapping(CouponServiceUris.UndoLog.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(undoLogService.removeByIds(ids));
    }
}
