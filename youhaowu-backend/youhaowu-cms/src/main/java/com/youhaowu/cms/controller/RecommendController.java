package com.youhaowu.cms.controller;

import com.youhaowu.common.constant.CmsServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.R;
import com.youhaowu.cms.dto.RecommendQueryDTO;
import com.youhaowu.cms.dto.RecommendSaveDTO;
import com.youhaowu.cms.service.RecommendService;
import com.youhaowu.cms.vo.RecommendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 今日推荐控制器
 */
@RestController
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    /**
     * 获取启用的推荐列表（首页使用）
     */
    @GetMapping(CmsServiceUris.CmsRecommend.LIST)
    public R<List<RecommendVO>> list() {
        return R.ok(recommendService.listEnabled());
    }

    /**
     * 查询推荐详情
     */
    @GetMapping(CmsServiceUris.CmsRecommend.INFO)
    public R<RecommendVO> info(@PathVariable Long id) {
        return R.ok(recommendService.getById(id));
    }

    /**
     * 分页查询推荐列表（管理端）
     */
    @PostMapping(CmsServiceUris.CmsRecommend.PAGE)
    public R<PageData<RecommendVO>> page(@RequestBody RecommendQueryDTO query) {
        return R.ok(recommendService.page(query));
    }

    /**
     * 新增/编辑推荐（id 空=新增，id 非空=更新）
     */
    @PostMapping(CmsServiceUris.CmsRecommend.SAVE)
    public R<Integer> save(@RequestBody RecommendSaveDTO dto) {
        return R.ok(recommendService.save(dto));
    }

    /**
     * 批量删除推荐
     */
    @PostMapping(CmsServiceUris.CmsRecommend.DELETE)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(recommendService.deleteBatch(ids));
    }
}
