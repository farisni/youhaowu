package com.wheatmall.member.controller;

import com.wheatmall.common.constant.MemberServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.member.dto.MemberCollectSubjectQueryDTO;
import com.wheatmall.member.service.MemberCollectSubjectService;
import com.wheatmall.member.vo.MemberCollectSubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberCollectSubjectController {

    @Autowired
    private MemberCollectSubjectService memberCollectSubjectService;

    @GetMapping(MemberServiceUris.MemberCollectSubject.PAGE)
    public R<PageData<MemberCollectSubjectVO>> page(MemberCollectSubjectQueryDTO query) {
        return R.ok(memberCollectSubjectService.page(query));
    }

    @GetMapping(MemberServiceUris.MemberCollectSubject.INFO)
    public R<MemberCollectSubjectVO> info(@PathVariable Long id) {
        return R.ok(memberCollectSubjectService.getById(id));
    }

    @PostMapping(MemberServiceUris.MemberCollectSubject.SAVE)
    public R<Integer> save(@RequestBody MemberCollectSubjectVO vo) {
        return R.ok(memberCollectSubjectService.save(vo));
    }

    @PostMapping(MemberServiceUris.MemberCollectSubject.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberCollectSubjectVO> list) {
        return R.ok(memberCollectSubjectService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.MemberCollectSubject.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberCollectSubjectVO vo) {
        return R.ok(memberCollectSubjectService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSubject.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberCollectSubjectService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSubject.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberCollectSubjectService.removeByIds(ids));
    }
}
