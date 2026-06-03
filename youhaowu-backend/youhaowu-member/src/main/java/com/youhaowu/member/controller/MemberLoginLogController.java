package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<MemberLoginLogVO> page(MemberLoginLogQueryDTO query) {
        return memberLoginLogService.page(query);
    }

    @GetMapping(MemberServiceUris.MemberLoginLog.INFO)
    public MemberLoginLogVO info(@PathVariable Long id) {
        return memberLoginLogService.getById(id);
    }

    @PostMapping(MemberServiceUris.MemberLoginLog.SAVE)
    public Integer save(@RequestBody MemberLoginLogVO vo) {
        return memberLoginLogService.save(vo);
    }

    @PostMapping(MemberServiceUris.MemberLoginLog.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberLoginLogVO> list) {
        return memberLoginLogService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.MemberLoginLog.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberLoginLogVO vo) {
        return memberLoginLogService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.MemberLoginLog.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberLoginLogService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.MemberLoginLog.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberLoginLogService.removeByIds(ids);
    }
}
