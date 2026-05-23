package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.query.AttrQueryDTO;
import com.wheatmall.product.service.AttrService;
import com.wheatmall.product.vo.AttrRespVo;
import com.wheatmall.product.vo.AttrVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品属性控制器
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_ATTR_SERVICE)
public class AttrController {

    @Autowired
    private AttrService attrService;

    /**
     * 分页查询基本属性
     */
    @GetMapping(ServiceUris.ProductAttr.BASE_PAGE)
    public R<PageData<AttrRespVo>> baseAttrList(AttrQueryDTO query, @PathVariable Long catelogId) {
        return R.ok(attrService.queryBaseAttrPage(query, catelogId, "base"));
    }

    /**
     * 分页查询销售属性
     */
    @GetMapping(ServiceUris.ProductAttr.SALE_PAGE)
    public R<PageData<AttrRespVo>> saleAttrList(AttrQueryDTO query, @PathVariable Long catelogId) {
        return R.ok(attrService.queryBaseAttrPage(query, catelogId, "sale"));
    }

    /**
     * 查询属性详情
     */
    @GetMapping(ServiceUris.ProductAttr.INFO)
    public R<AttrRespVo> info(@PathVariable Long attrId) {
        return R.ok(attrService.getAttrInfo(attrId));
    }

    /**
     * 保存属性
     */
    @PostMapping(ServiceUris.ProductAttr.SAVE)
    public R<Void> save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);
        return R.ok();
    }

    /**
     * 更新属性
     */
    @PostMapping(ServiceUris.ProductAttr.UPDATE)
    public R<Void> update(@PathVariable Long id, @RequestBody AttrVo attr) {
        attrService.updateAttrById(id, attr);
        return R.ok();
    }

    /**
     * 删除属性
     */
    @PostMapping(ServiceUris.ProductAttr.DELETE)
    public R<Void> delete(@PathVariable Long id) {
        attrService.removeById(id);
        return R.ok();
    }

    /**
     * 查询属性关联关系
     */
    @GetMapping(ServiceUris.ProductAttr.RELATION_PAGE)
    public R<List<AttrRespVo>> relation(@PathVariable Long attrgroupId) {
        return R.ok(attrService.getRelationAttr(attrgroupId));
    }

    /**
     * 查询未关联属性
     */
    @GetMapping(ServiceUris.ProductAttr.NO_RELATION_PAGE)
    public R<PageData<?>> noRelation(AttrQueryDTO query, @PathVariable Long attrgroupId) {
        return R.ok(attrService.getNoRelationAttr(query, attrgroupId));
    }
}
