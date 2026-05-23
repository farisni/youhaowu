package com.wheatmall.member.controller;

import com.wheatmall.common.constant.MemberServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.member.dto.MemberReceiveAddressQueryDTO;
import com.wheatmall.member.service.MemberReceiveAddressService;
import com.wheatmall.member.vo.MemberReceiveAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberReceiveAddressController {

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @GetMapping(MemberServiceUris.MemberReceiveAddress.PAGE)
    public R<PageData<MemberReceiveAddressVO>> page(MemberReceiveAddressQueryDTO query) {
        return R.ok(memberReceiveAddressService.page(query));
    }

    @GetMapping(MemberServiceUris.MemberReceiveAddress.INFO)
    public R<MemberReceiveAddressVO> info(@PathVariable Long id) {
        return R.ok(memberReceiveAddressService.getById(id));
    }

    @PostMapping(MemberServiceUris.MemberReceiveAddress.SAVE)
    public R<Integer> save(@RequestBody MemberReceiveAddressVO vo) {
        return R.ok(memberReceiveAddressService.save(vo));
    }

    @PostMapping(MemberServiceUris.MemberReceiveAddress.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberReceiveAddressVO> list) {
        return R.ok(memberReceiveAddressService.saveBatch(list));
    }

    @PutMapping(MemberServiceUris.MemberReceiveAddress.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberReceiveAddressVO vo) {
        return R.ok(memberReceiveAddressService.updateById(id, vo));
    }

    @DeleteMapping(MemberServiceUris.MemberReceiveAddress.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberReceiveAddressService.removeById(id));
    }

    @DeleteMapping(MemberServiceUris.MemberReceiveAddress.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberReceiveAddressService.removeByIds(ids));
    }
}
