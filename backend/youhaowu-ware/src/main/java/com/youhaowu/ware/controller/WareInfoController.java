package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.ware.dto.WareInfoQueryDTO;
import com.youhaowu.ware.service.WareInfoService;
import com.youhaowu.ware.vo.WareInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WareInfo 控制器
 */
@RestController
public class WareInfoController {

    @Autowired
    private WareInfoService wareInfoService;

    @GetMapping(WareServiceUris.WareInfo.PAGE)
    public R<PageData<WareInfoVO>> list(WareInfoQueryDTO query) {
        return R.ok(wareInfoService.page(query));
    }

    @GetMapping(WareServiceUris.WareInfo.INFO)
    public R<WareInfoVO> info(@PathVariable Long id) {
        return R.ok(wareInfoService.getById(id));
    }

    @GetMapping(WareServiceUris.WareInfo.SAVE)
    public R<Integer> save(@RequestBody WareInfoVO vo) {
        return R.ok(wareInfoService.save(vo));
    }

    @GetMapping(WareServiceUris.WareInfo.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody WareInfoVO vo) {
        return R.ok(wareInfoService.updateById(id, vo));
    }

    @GetMapping(WareServiceUris.WareInfo.DELETE)
    public R<Integer> delete(@RequestBody List<Long> ids) {
        return R.ok(wareInfoService.removeByIds(ids));
    }
}
