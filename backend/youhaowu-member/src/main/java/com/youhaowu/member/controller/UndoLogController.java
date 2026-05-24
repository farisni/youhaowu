package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.member.dto.UndoLogQueryDTO;
import com.youhaowu.member.service.UndoLogService;
import com.youhaowu.member.vo.UndoLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;

    @GetMapping(MemberServiceUris.UndoLog.PAGE)
    public R<PageData<UndoLogVO>> page(UndoLogQueryDTO query) {
        return R.ok(undoLogService.page(query));
    }

    @GetMapping(MemberServiceUris.UndoLog.INFO)
    public R<UndoLogVO> info(@PathVariable Long id) {
        return R.ok(undoLogService.getById(id));
    }

    @PostMapping(MemberServiceUris.UndoLog.SAVE)
    public R<Integer> save(@RequestBody UndoLogVO vo) {
        return R.ok(undoLogService.save(vo));
    }

    @PostMapping(MemberServiceUris.UndoLog.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<UndoLogVO> list) {
        return R.ok(undoLogService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.UndoLog.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody UndoLogVO vo) {
        return R.ok(undoLogService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.UndoLog.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(undoLogService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.UndoLog.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(undoLogService.removeByIds(ids));
    }
}
