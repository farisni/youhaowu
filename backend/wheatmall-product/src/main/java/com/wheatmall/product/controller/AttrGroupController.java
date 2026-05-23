package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ProductServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.query.AttrGroupQueryDTO;
import com.wheatmall.product.service.AttrGroupService;
import com.wheatmall.product.vo.AttrGroupVO;
import com.wheatmall.product.vo.AttrGroupWithAttrsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性分组控制器
 */
@RestController
@RequestMapping(ProductServiceUris.PRODUCT_ATTRGROUP_SERVICE)
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 分页查询属性分组
     */
    @GetMapping(ProductServiceUris.ProductAttrGroup.PAGE)
    public R<PageData<AttrGroupVO>> list(AttrGroupQueryDTO query, @PathVariable Long catelogId) {
        return R.ok(attrGroupService.page(query, catelogId));
    }

    /**
     * 查询属性分组详情
     */
    @GetMapping(ProductServiceUris.ProductAttrGroup.INFO)
    public R<AttrGroupVO> info(@PathVariable Long attrGroupId) {
        return R.ok(attrGroupService.getById(attrGroupId));
    }

    /**
     * 保存属性分组
     */
    @PostMapping(ProductServiceUris.ProductAttrGroup.SAVE)
    public R<Integer> save(@RequestBody AttrGroupVO vo) {
        return R.ok(attrGroupService.save(vo));
    }

    /**
     * 更新属性分组
     */
    @PostMapping(ProductServiceUris.ProductAttrGroup.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody AttrGroupVO vo) {
        return R.ok(attrGroupService.updateById(id, vo));
    }

    /**
     * 删除属性分组
     */
    @PostMapping(ProductServiceUris.ProductAttrGroup.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(attrGroupService.removeById(id));
    }

    /**
     * 查询分组及关联属性
     */
    @GetMapping(ProductServiceUris.ProductAttrGroup.WITH_ATTRS)
    public R<List<AttrGroupWithAttrsVO>> withAttrs(@PathVariable Long catelogId) {
        return R.ok(attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId));
    }
}
