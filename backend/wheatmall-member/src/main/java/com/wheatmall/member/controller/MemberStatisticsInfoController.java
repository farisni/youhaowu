package com.wheatmall.member.controller;

import com.wheatmall.common.constant.MemberServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.member.dto.MemberStatisticsInfoQueryDTO;
import com.wheatmall.member.service.MemberStatisticsInfoService;
import com.wheatmall.member.vo.MemberStatisticsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberStatisticsInfoController {

    @Autowired
    private MemberStatisticsInfoService memberStatisticsInfoService;

    @GetMapping(MemberServiceUris.MemberStatisticsInfo.PAGE)
    public R<PageData<MemberStatisticsInfoVO>> page(MemberStatisticsInfoQueryDTO query) {
        return R.ok(memberStatisticsInfoService.page(query));
    }

    @GetMapping(MemberServiceUris.MemberStatisticsInfo.INFO)
    public R<MemberStatisticsInfoVO> info(@PathVariable Long id) {
        return R.ok(memberStatisticsInfoService.getById(id));
    }

    @PostMapping(MemberServiceUris.MemberStatisticsInfo.SAVE)
    public R<Integer> save(@RequestBody MemberStatisticsInfoVO vo) {
        return R.ok(memberStatisticsInfoService.save(vo));
    }

    @PostMapping(MemberServiceUris.MemberStatisticsInfo.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberStatisticsInfoVO> list) {
        return R.ok(memberStatisticsInfoService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.MemberStatisticsInfo.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberStatisticsInfoVO vo) {
        return R.ok(memberStatisticsInfoService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.MemberStatisticsInfo.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberStatisticsInfoService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.MemberStatisticsInfo.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberStatisticsInfoService.removeByIds(ids));
    }
}
