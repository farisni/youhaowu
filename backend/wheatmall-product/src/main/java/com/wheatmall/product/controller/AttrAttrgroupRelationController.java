package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.service.AttrAttrgroupRelationService;
import com.wheatmall.product.query.AttrQueryDTO;
import com.wheatmall.product.vo.AttrAttrgroupRelationVO;
import com.wheatmall.product.vo.AttrGroupRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性&属性分组关联控制器
 */
@RestController
public class AttrAttrgroupRelationController {

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 分页查询关联关系
     */
    @GetMapping(ProductServiceUris.ProductAttr.SAVE_RELATION)
    public R<PageData<AttrAttrgroupRelationVO>> list(AttrQueryDTO query) {
        return R.ok(relationService.page(query));
    }

    /**
     * 保存关联关系
     */
    @PostMapping(ProductServiceUris.ProductAttr.SAVE_RELATION)
    public R<Void> saveRelation(@RequestBody List<AttrGroupRelationVO> vos) {
        relationService.saveRelationBatch(vos);
        return R.ok();
    }
}
