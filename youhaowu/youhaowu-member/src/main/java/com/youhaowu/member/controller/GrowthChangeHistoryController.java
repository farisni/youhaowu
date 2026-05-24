package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.member.dto.GrowthChangeHistoryQueryDTO;
import com.youhaowu.member.service.GrowthChangeHistoryService;
import com.youhaowu.member.vo.GrowthChangeHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GrowthChangeHistoryController {

    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    @GetMapping(MemberServiceUris.GrowthChangeHistory.PAGE)
    public R<PageData<GrowthChangeHistoryVO>> page(GrowthChangeHistoryQueryDTO query) {
        return R.ok(growthChangeHistoryService.page(query));
    }

    @GetMapping(MemberServiceUris.GrowthChangeHistory.INFO)
    public R<GrowthChangeHistoryVO> info(@PathVariable Long id) {
        return R.ok(growthChangeHistoryService.getById(id));
    }

    @PostMapping(MemberServiceUris.GrowthChangeHistory.SAVE)
    public R<Integer> save(@RequestBody GrowthChangeHistoryVO vo) {
        return R.ok(growthChangeHistoryService.save(vo));
    }

    @PostMapping(MemberServiceUris.GrowthChangeHistory.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<GrowthChangeHistoryVO> list) {
        return R.ok(growthChangeHistoryService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.GrowthChangeHistory.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody GrowthChangeHistoryVO vo) {
        return R.ok(growthChangeHistoryService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.GrowthChangeHistory.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(growthChangeHistoryService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.GrowthChangeHistory.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(growthChangeHistoryService.removeByIds(ids));
    }
}
