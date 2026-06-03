package com.youhaowu.coupon.controller;

import com.youhaowu.common.constant.CouponServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<UndoLogVO> page(UndoLogQueryDTO query) {
        return undoLogService.page(query);
    }

    @GetMapping(CouponServiceUris.UndoLog.INFO)
    public UndoLogVO info(@PathVariable Long id) {
        return undoLogService.getById(id);
    }

    @PostMapping(CouponServiceUris.UndoLog.SAVE)
    public Integer save(@RequestBody UndoLogVO vo) {
        return undoLogService.save(vo);
    }

    @PostMapping(CouponServiceUris.UndoLog.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<UndoLogVO> list) {
        return undoLogService.saveBatch(list);
    }

    @PutMapping(CouponServiceUris.UndoLog.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody UndoLogVO vo) {
        return undoLogService.updateById(id, vo);
    }

    @DeleteMapping(CouponServiceUris.UndoLog.DELETE)
    public Integer delete(@PathVariable Long id) {
        return undoLogService.removeById(id);
    }

    @DeleteMapping(CouponServiceUris.UndoLog.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return undoLogService.removeByIds(ids);
    }
}
