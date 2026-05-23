package com.wheatmall.member.controller;

import com.wheatmall.common.constant.MemberServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.member.dto.MemberLevelQueryDTO;
import com.wheatmall.member.service.MemberLevelService;
import com.wheatmall.member.vo.MemberLevelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberLevelController {

    @Autowired
    private MemberLevelService memberLevelService;

    @GetMapping(MemberServiceUris.MemberLevel.PAGE)
    public R<PageData<MemberLevelVO>> page(MemberLevelQueryDTO query) {
        return R.ok(memberLevelService.page(query));
    }

    @GetMapping(MemberServiceUris.MemberLevel.INFO)
    public R<MemberLevelVO> info(@PathVariable Long id) {
        return R.ok(memberLevelService.getById(id));
    }

    @PostMapping(MemberServiceUris.MemberLevel.SAVE)
    public R<Integer> save(@RequestBody MemberLevelVO vo) {
        return R.ok(memberLevelService.save(vo));
    }

    @PostMapping(MemberServiceUris.MemberLevel.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberLevelVO> list) {
        return R.ok(memberLevelService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.MemberLevel.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberLevelVO vo) {
        return R.ok(memberLevelService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.MemberLevel.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberLevelService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.MemberLevel.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberLevelService.removeByIds(ids));
    }
}
