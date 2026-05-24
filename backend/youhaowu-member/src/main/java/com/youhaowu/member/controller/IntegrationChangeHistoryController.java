package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.member.dto.IntegrationChangeHistoryQueryDTO;
import com.youhaowu.member.service.IntegrationChangeHistoryService;
import com.youhaowu.member.vo.IntegrationChangeHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IntegrationChangeHistoryController {

    @Autowired
    private IntegrationChangeHistoryService integrationChangeHistoryService;

    @GetMapping(MemberServiceUris.IntegrationChangeHistory.PAGE)
    public R<PageData<IntegrationChangeHistoryVO>> page(IntegrationChangeHistoryQueryDTO query) {
        return R.ok(integrationChangeHistoryService.page(query));
    }

    @GetMapping(MemberServiceUris.IntegrationChangeHistory.INFO)
    public R<IntegrationChangeHistoryVO> info(@PathVariable Long id) {
        return R.ok(integrationChangeHistoryService.getById(id));
    }

    @PostMapping(MemberServiceUris.IntegrationChangeHistory.SAVE)
    public R<Integer> save(@RequestBody IntegrationChangeHistoryVO vo) {
        return R.ok(integrationChangeHistoryService.save(vo));
    }

    @PostMapping(MemberServiceUris.IntegrationChangeHistory.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<IntegrationChangeHistoryVO> list) {
        return R.ok(integrationChangeHistoryService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.IntegrationChangeHistory.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody IntegrationChangeHistoryVO vo) {
        return R.ok(integrationChangeHistoryService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.IntegrationChangeHistory.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(integrationChangeHistoryService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.IntegrationChangeHistory.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(integrationChangeHistoryService.removeByIds(ids));
    }
}
