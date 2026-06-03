package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberCollectSpuQueryDTO;
import com.youhaowu.member.service.MemberCollectSpuService;
import com.youhaowu.member.vo.MemberCollectSpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberCollectSpuController {

    @Autowired
    private MemberCollectSpuService memberCollectSpuService;

    @GetMapping(MemberServiceUris.MemberCollectSpu.PAGE)
    public PageData<MemberCollectSpuVO> page(MemberCollectSpuQueryDTO query) {
        return memberCollectSpuService.page(query);
    }

    @GetMapping(MemberServiceUris.MemberCollectSpu.INFO)
    public MemberCollectSpuVO info(@PathVariable Long id) {
        return memberCollectSpuService.getById(id);
    }

    @PostMapping(MemberServiceUris.MemberCollectSpu.SAVE)
    public Integer save(@RequestBody MemberCollectSpuVO vo) {
        return memberCollectSpuService.save(vo);
    }

    @PostMapping(MemberServiceUris.MemberCollectSpu.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberCollectSpuVO> list) {
        return memberCollectSpuService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.MemberCollectSpu.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberCollectSpuVO vo) {
        return memberCollectSpuService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSpu.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberCollectSpuService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSpu.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberCollectSpuService.removeByIds(ids);
    }
}
