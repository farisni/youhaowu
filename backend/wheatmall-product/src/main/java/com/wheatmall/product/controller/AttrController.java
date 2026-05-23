package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.query.AttrQueryDTO;
import com.wheatmall.product.service.AttrService;
import com.wheatmall.product.vo.AttrRespVO;
import com.wheatmall.product.vo.AttrVO;
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
    public R<PageData<AttrRespVO>> baseAttrList(AttrQueryDTO query, @PathVariable Long catelogId) {
        return R.ok(attrService.queryBaseAttrPage(query, catelogId, "base"));
    }

    /**
     * 分页查询销售属性
     */
    @GetMapping(ProductServiceUris.ProductAttr.SALE_PAGE)
    public R<PageData<AttrRespVO>> saleAttrList(AttrQueryDTO query, @PathVariable Long catelogId) {
        return R.ok(attrService.queryBaseAttrPage(query, catelogId, "sale"));
    }

    /**
     * 查询属性详情
     */
    @GetMapping(ProductServiceUris.ProductAttr.INFO)
    public R<AttrRespVO> info(@PathVariable Long attrId) {
        return R.ok(attrService.getAttrInfo(attrId));
    }

    /**
     * 保存属性
     */
    @PostMapping(ProductServiceUris.ProductAttr.SAVE)
    public R<Integer> save(@RequestBody AttrVO attr) {
        return R.ok(attrService.saveAttr(attr));
    }

    /**
     * 更新属性
     */
    @PostMapping(ProductServiceUris.ProductAttr.UPDATE)
    public R<Void> update(@PathVariable Long id, @RequestBody AttrVO attr) {
        attrService.updateAttrById(id, attr);
        return R.ok();
    }

    /**
     * 删除属性
     */
    @PostMapping(ProductServiceUris.ProductAttr.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(attrService.removeById(id));
    }

    /**
     * 查询属性关联关系
     */
    @GetMapping(ProductServiceUris.ProductAttr.RELATION_PAGE)
    public R<List<AttrRespVO>> relation(@PathVariable Long attrgroupId) {
        return R.ok(attrService.getRelationAttr(attrgroupId));
    }

    /**
     * 查询未关联属性
     */
    @GetMapping(ProductServiceUris.ProductAttr.NO_RELATION_PAGE)
    public R<PageData<?>> noRelation(AttrQueryDTO query, @PathVariable Long attrgroupId) {
        return R.ok(attrService.getNoRelationAttr(query, attrgroupId));
    }
}
