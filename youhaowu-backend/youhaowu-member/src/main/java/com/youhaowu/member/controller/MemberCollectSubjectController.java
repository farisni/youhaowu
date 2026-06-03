package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberCollectSubjectQueryDTO;
import com.youhaowu.member.service.MemberCollectSubjectService;
import com.youhaowu.member.vo.MemberCollectSubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberCollectSubjectController {

    @Autowired
    private MemberCollectSubjectService memberCollectSubjectService;

    @GetMapping(MemberServiceUris.MemberCollectSubject.PAGE)
    public PageData<MemberCollectSubjectVO> page(MemberCollectSubjectQueryDTO query) {
        return memberCollectSubjectService.page(query);
    }

    @GetMapping(MemberServiceUris.MemberCollectSubject.INFO)
    public MemberCollectSubjectVO info(@PathVariable Long id) {
        return memberCollectSubjectService.getById(id);
    }

    @PostMapping(MemberServiceUris.MemberCollectSubject.SAVE)
    public Integer save(@RequestBody MemberCollectSubjectVO vo) {
        return memberCollectSubjectService.save(vo);
    }

    @PostMapping(MemberServiceUris.MemberCollectSubject.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberCollectSubjectVO> list) {
        return memberCollectSubjectService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.MemberCollectSubject.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberCollectSubjectVO vo) {
        return memberCollectSubjectService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSubject.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberCollectSubjectService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSubject.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberCollectSubjectService.removeByIds(ids);
    }
}
