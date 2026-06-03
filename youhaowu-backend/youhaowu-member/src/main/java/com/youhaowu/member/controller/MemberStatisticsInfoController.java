package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberStatisticsInfoQueryDTO;
import com.youhaowu.member.service.MemberStatisticsInfoService;
import com.youhaowu.member.vo.MemberStatisticsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberStatisticsInfoController {

    @Autowired
    private MemberStatisticsInfoService memberStatisticsInfoService;

    @GetMapping(MemberServiceUris.MemberStatisticsInfo.PAGE)
    public PageData<MemberStatisticsInfoVO> page(MemberStatisticsInfoQueryDTO query) {
        return memberStatisticsInfoService.page(query);
    }

    @GetMapping(MemberServiceUris.MemberStatisticsInfo.INFO)
    public MemberStatisticsInfoVO info(@PathVariable Long id) {
        return memberStatisticsInfoService.getById(id);
    }

    @PostMapping(MemberServiceUris.MemberStatisticsInfo.SAVE)
    public Integer save(@RequestBody MemberStatisticsInfoVO vo) {
        return memberStatisticsInfoService.save(vo);
    }

    @PostMapping(MemberServiceUris.MemberStatisticsInfo.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberStatisticsInfoVO> list) {
        return memberStatisticsInfoService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.MemberStatisticsInfo.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberStatisticsInfoVO vo) {
        return memberStatisticsInfoService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.MemberStatisticsInfo.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberStatisticsInfoService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.MemberStatisticsInfo.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberStatisticsInfoService.removeByIds(ids);
    }
}
