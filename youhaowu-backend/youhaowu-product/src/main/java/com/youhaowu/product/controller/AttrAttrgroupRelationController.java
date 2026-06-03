package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.service.AttrAttrgroupRelationService;
import com.youhaowu.product.query.AttrQueryDTO;
import com.youhaowu.product.vo.AttrAttrgroupRelationVO;
import com.youhaowu.product.vo.AttrGroupRelationVO;
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
    public PageData<AttrAttrgroupRelationVO> list(AttrQueryDTO query) {
        return relationService.page(query);
    }

    /**
     * 保存关联关系
     */
    @PostMapping(ProductServiceUris.ProductAttr.SAVE_RELATION)
    public Object saveRelation(@RequestBody List<AttrGroupRelationVO> vos) {
        relationService.saveRelationBatch(vos);
        return null;
    }
}
