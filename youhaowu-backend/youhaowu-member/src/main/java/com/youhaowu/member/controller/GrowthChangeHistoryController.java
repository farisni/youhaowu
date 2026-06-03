package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<GrowthChangeHistoryVO> page(GrowthChangeHistoryQueryDTO query) {
        return growthChangeHistoryService.page(query);
    }

    @GetMapping(MemberServiceUris.GrowthChangeHistory.INFO)
    public GrowthChangeHistoryVO info(@PathVariable Long id) {
        return growthChangeHistoryService.getById(id);
    }

    @PostMapping(MemberServiceUris.GrowthChangeHistory.SAVE)
    public Integer save(@RequestBody GrowthChangeHistoryVO vo) {
        return growthChangeHistoryService.save(vo);
    }

    @PostMapping(MemberServiceUris.GrowthChangeHistory.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<GrowthChangeHistoryVO> list) {
        return growthChangeHistoryService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.GrowthChangeHistory.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody GrowthChangeHistoryVO vo) {
        return growthChangeHistoryService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.GrowthChangeHistory.DELETE)
    public Integer delete(@PathVariable Long id) {
        return growthChangeHistoryService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.GrowthChangeHistory.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return growthChangeHistoryService.removeByIds(ids);
    }
}
