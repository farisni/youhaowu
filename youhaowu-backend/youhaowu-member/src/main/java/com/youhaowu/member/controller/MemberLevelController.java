package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberLevelQueryDTO;
import com.youhaowu.member.service.MemberLevelService;
import com.youhaowu.member.vo.MemberLevelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberLevelController {

    @Autowired
    private MemberLevelService memberLevelService;

    @GetMapping(MemberServiceUris.MemberLevel.PAGE)
    public PageData<MemberLevelVO> page(MemberLevelQueryDTO query) {
        return memberLevelService.page(query);
    }

    @GetMapping(MemberServiceUris.MemberLevel.INFO)
    public MemberLevelVO info(@PathVariable Long id) {
        return memberLevelService.getById(id);
    }

    @PostMapping(MemberServiceUris.MemberLevel.SAVE)
    public Integer save(@RequestBody MemberLevelVO vo) {
        return memberLevelService.save(vo);
    }

    @PostMapping(MemberServiceUris.MemberLevel.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberLevelVO> list) {
        return memberLevelService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.MemberLevel.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberLevelVO vo) {
        return memberLevelService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.MemberLevel.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberLevelService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.MemberLevel.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberLevelService.removeByIds(ids);
    }
}
