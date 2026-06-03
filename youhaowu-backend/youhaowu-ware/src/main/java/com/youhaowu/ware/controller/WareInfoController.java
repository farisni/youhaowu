package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
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
    public PageData<WareInfoVO> list(WareInfoQueryDTO query) {
        return wareInfoService.page(query);
    }

    @GetMapping(WareServiceUris.WareInfo.INFO)
    public WareInfoVO info(@PathVariable Long id) {
        return wareInfoService.getById(id);
    }

    @GetMapping(WareServiceUris.WareInfo.SAVE)
    public Integer save(@RequestBody WareInfoVO vo) {
        return wareInfoService.save(vo);
    }

    @GetMapping(WareServiceUris.WareInfo.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody WareInfoVO vo) {
        return wareInfoService.updateById(id, vo);
    }

    @GetMapping(WareServiceUris.WareInfo.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return wareInfoService.removeByIds(ids);
    }
}
