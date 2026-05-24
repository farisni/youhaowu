package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.member.dto.MemberLoginLogQueryDTO;
import com.youhaowu.member.service.MemberLoginLogService;
import com.youhaowu.member.vo.MemberLoginLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberLoginLogController {

    @Autowired
    private MemberLoginLogService memberLoginLogService;

    @GetMapping(MemberServiceUris.MemberLoginLog.PAGE)
    public R<PageData<MemberLoginLogVO>> page(MemberLoginLogQueryDTO query) {
        return R.ok(memberLoginLogService.page(query));
    }

    @GetMapping(MemberServiceUris.MemberLoginLog.INFO)
    public R<MemberLoginLogVO> info(@PathVariable Long id) {
        return R.ok(memberLoginLogService.getById(id));
    }

    @PostMapping(MemberServiceUris.MemberLoginLog.SAVE)
    public R<Integer> save(@RequestBody MemberLoginLogVO vo) {
        return R.ok(memberLoginLogService.save(vo));
    }

    @PostMapping(MemberServiceUris.MemberLoginLog.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberLoginLogVO> list) {
        return R.ok(memberLoginLogService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.MemberLoginLog.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberLoginLogVO vo) {
        return R.ok(memberLoginLogService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.MemberLoginLog.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberLoginLogService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.MemberLoginLog.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberLoginLogService.removeByIds(ids));
    }
}
