package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<UndoLogVO> page(UndoLogQueryDTO query) {
        return undoLogService.page(query);
    }

    @GetMapping(MemberServiceUris.UndoLog.INFO)
    public UndoLogVO info(@PathVariable Long id) {
        return undoLogService.getById(id);
    }

    @PostMapping(MemberServiceUris.UndoLog.SAVE)
    public Integer save(@RequestBody UndoLogVO vo) {
        return undoLogService.save(vo);
    }

    @PostMapping(MemberServiceUris.UndoLog.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<UndoLogVO> list) {
        return undoLogService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.UndoLog.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody UndoLogVO vo) {
        return undoLogService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.UndoLog.DELETE)
    public Integer delete(@PathVariable Long id) {
        return undoLogService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.UndoLog.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return undoLogService.removeByIds(ids);
    }
}
