package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<IntegrationChangeHistoryVO> page(IntegrationChangeHistoryQueryDTO query) {
        return integrationChangeHistoryService.page(query);
    }

    @GetMapping(MemberServiceUris.IntegrationChangeHistory.INFO)
    public IntegrationChangeHistoryVO info(@PathVariable Long id) {
        return integrationChangeHistoryService.getById(id);
    }

    @PostMapping(MemberServiceUris.IntegrationChangeHistory.SAVE)
    public Integer save(@RequestBody IntegrationChangeHistoryVO vo) {
        return integrationChangeHistoryService.save(vo);
    }

    @PostMapping(MemberServiceUris.IntegrationChangeHistory.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<IntegrationChangeHistoryVO> list) {
        return integrationChangeHistoryService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.IntegrationChangeHistory.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody IntegrationChangeHistoryVO vo) {
        return integrationChangeHistoryService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.IntegrationChangeHistory.DELETE)
    public Integer delete(@PathVariable Long id) {
        return integrationChangeHistoryService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.IntegrationChangeHistory.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return integrationChangeHistoryService.removeByIds(ids);
    }
}
