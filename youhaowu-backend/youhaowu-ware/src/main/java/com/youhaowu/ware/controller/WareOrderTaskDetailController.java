package com.youhaowu.ware.controller;

import com.youhaowu.common.constant.WareServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.ware.dto.WareOrderTaskDetailQueryDTO;
import com.youhaowu.ware.service.WareOrderTaskDetailService;
import com.youhaowu.ware.vo.WareOrderTaskDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WareOrderTaskDetail 控制器
 */
@RestController
public class WareOrderTaskDetailController {

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @GetMapping(WareServiceUris.WareOrderTaskDetail.PAGE)
    public PageData<WareOrderTaskDetailVO> list(WareOrderTaskDetailQueryDTO query) {
        return wareOrderTaskDetailService.page(query);
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.INFO)
    public WareOrderTaskDetailVO info(@PathVariable Long id) {
        return wareOrderTaskDetailService.getById(id);
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.SAVE)
    public Integer save(@RequestBody WareOrderTaskDetailVO vo) {
        return wareOrderTaskDetailService.save(vo);
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody WareOrderTaskDetailVO vo) {
        return wareOrderTaskDetailService.updateById(id, vo);
    }

    @GetMapping(WareServiceUris.WareOrderTaskDetail.DELETE)
    public Integer delete(@RequestBody List<Long> ids) {
        return wareOrderTaskDetailService.removeByIds(ids);
    }
}
