package com.youhaowu.cms.controller;

import com.youhaowu.common.constant.CmsServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.cms.dto.RankQueryDTO;
import com.youhaowu.cms.dto.RankSaveDTO;
import com.youhaowu.cms.service.RankService;
import com.youhaowu.cms.vo.RankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品排行控制器
 */
@RestController
public class RankController {

    @Autowired
    private RankService rankService;

    /**
     * 按 tab 获取排行列表（首页使用）
     */
    @GetMapping(CmsServiceUris.CmsRank.LIST)
    public List<RankVO> list(@RequestParam String tab) {
        return rankService.listByTab(tab);
    }

    /**
     * 查询排行详情
     */
    @GetMapping(CmsServiceUris.CmsRank.INFO)
    public RankVO info(@PathVariable Long id) {
        return rankService.getById(id);
    }

    /**
     * 分页查询排行列表（管理端）
     */
    @PostMapping(CmsServiceUris.CmsRank.PAGE)
    public PageData<RankVO> page(@RequestBody RankQueryDTO query) {
        return rankService.page(query);
    }

    /**
     * 新增/编辑排行（id 空=新增，id 非空=更新）
     */
    @PostMapping(CmsServiceUris.CmsRank.SAVE)
    public Integer save(@RequestBody RankSaveDTO dto) {
        return rankService.save(dto);
    }

    /**
     * 批量删除排行
     */
    @PostMapping(CmsServiceUris.CmsRank.DELETE)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return rankService.deleteBatch(ids);
    }
}
