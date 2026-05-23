package com.wheatmall.member.controller;

import com.wheatmall.common.constant.MemberServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.member.dto.MemberCollectSpuQueryDTO;
import com.wheatmall.member.service.MemberCollectSpuService;
import com.wheatmall.member.vo.MemberCollectSpuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberCollectSpuController {

    @Autowired
    private MemberCollectSpuService memberCollectSpuService;

    @GetMapping(MemberServiceUris.MemberCollectSpu.PAGE)
    public R<PageData<MemberCollectSpuVO>> page(MemberCollectSpuQueryDTO query) {
        return R.ok(memberCollectSpuService.page(query));
    }

    @GetMapping(MemberServiceUris.MemberCollectSpu.INFO)
    public R<MemberCollectSpuVO> info(@PathVariable Long id) {
        return R.ok(memberCollectSpuService.getById(id));
    }

    @PostMapping(MemberServiceUris.MemberCollectSpu.SAVE)
    public R<Integer> save(@RequestBody MemberCollectSpuVO vo) {
        return R.ok(memberCollectSpuService.save(vo));
    }

    @PostMapping(MemberServiceUris.MemberCollectSpu.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberCollectSpuVO> list) {
        return R.ok(memberCollectSpuService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.MemberCollectSpu.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberCollectSpuVO vo) {
        return R.ok(memberCollectSpuService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSpu.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberCollectSpuService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.MemberCollectSpu.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberCollectSpuService.removeByIds(ids));
    }
}
