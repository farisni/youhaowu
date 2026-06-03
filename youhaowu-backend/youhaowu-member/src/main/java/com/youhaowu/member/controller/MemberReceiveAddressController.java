package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberReceiveAddressQueryDTO;
import com.youhaowu.member.service.MemberReceiveAddressService;
import com.youhaowu.member.vo.MemberReceiveAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberReceiveAddressController {

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @GetMapping(MemberServiceUris.MemberReceiveAddress.PAGE)
    public PageData<MemberReceiveAddressVO> page(MemberReceiveAddressQueryDTO query) {
        return memberReceiveAddressService.page(query);
    }

    @GetMapping(MemberServiceUris.MemberReceiveAddress.INFO)
    public MemberReceiveAddressVO info(@PathVariable Long id) {
        return memberReceiveAddressService.getById(id);
    }

    @PostMapping(MemberServiceUris.MemberReceiveAddress.SAVE)
    public Integer save(@RequestBody MemberReceiveAddressVO vo) {
        return memberReceiveAddressService.save(vo);
    }

    @PostMapping(MemberServiceUris.MemberReceiveAddress.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberReceiveAddressVO> list) {
        return memberReceiveAddressService.saveBatch(list);
    }

    @PutMapping(MemberServiceUris.MemberReceiveAddress.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberReceiveAddressVO vo) {
        return memberReceiveAddressService.updateById(id, vo);
    }

    @DeleteMapping(MemberServiceUris.MemberReceiveAddress.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberReceiveAddressService.removeById(id);
    }

    @DeleteMapping(MemberServiceUris.MemberReceiveAddress.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberReceiveAddressService.removeByIds(ids);
    }
}
