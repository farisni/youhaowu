package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.query.AttrQueryDTO;
import com.youhaowu.product.service.AttrService;
import com.youhaowu.product.vo.AttrRespVO;
import com.youhaowu.product.vo.AttrVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品属性控制器
 */
@RestController
public class AttrController {

    @Autowired
    private AttrService attrService;

    /**
     * 分页查询基本属性
     */
    @GetMapping(ProductServiceUris.ProductAttr.BASE_PAGE)
    public PageData<AttrRespVO> baseAttrList(AttrQueryDTO query, @PathVariable Long catelogId) {
        return attrService.queryBaseAttrPage(query, catelogId, "base");
    }

    /**
     * 分页查询销售属性
     */
    @GetMapping(ProductServiceUris.ProductAttr.SALE_PAGE)
    public PageData<AttrRespVO> saleAttrList(AttrQueryDTO query, @PathVariable Long catelogId) {
        return attrService.queryBaseAttrPage(query, catelogId, "sale");
    }

    /**
     * 查询属性详情
     */
    @GetMapping(ProductServiceUris.ProductAttr.INFO)
    public AttrRespVO info(@PathVariable Long attrId) {
        return attrService.getAttrInfo(attrId);
    }

    /**
     * 保存属性
     */
    @PostMapping(ProductServiceUris.ProductAttr.SAVE)
    public Integer save(@RequestBody AttrVO attr) {
        return attrService.saveAttr(attr);
    }

    /**
     * 更新属性
     */
    @PostMapping(ProductServiceUris.ProductAttr.UPDATE)
    public Object update(@PathVariable Long id, @RequestBody AttrVO attr) {
        attrService.updateAttrById(id, attr);
        
        return null;
    }

    /**
     * 删除属性
     */
    @PostMapping(ProductServiceUris.ProductAttr.DELETE)
    public Integer delete(@PathVariable Long id) {
        return attrService.removeById(id);
    }

    /**
     * 查询属性关联关系
     */
    @GetMapping(ProductServiceUris.ProductAttr.RELATION_PAGE)
    public List<AttrRespVO> relation(@PathVariable Long attrgroupId) {
        return attrService.getRelationAttr(attrgroupId);
    }

    /**
     * 查询未关联属性
     */
    @GetMapping(ProductServiceUris.ProductAttr.NO_RELATION_PAGE)
    public PageData<?> noRelation(AttrQueryDTO query, @PathVariable Long attrgroupId) {
        return attrService.getNoRelationAttr(query, attrgroupId);
    }
}
