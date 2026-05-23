package com.wheatmall.product.controller;

import com.wheatmall.common.constant.ServiceUris;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.product.query.AttrGroupQueryDTO;
import com.wheatmall.product.service.AttrGroupService;
import com.wheatmall.product.vo.AttrGroupVO;
import com.wheatmall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性分组控制器
 */
@RestController
@RequestMapping(ServiceUris.PRODUCT_ATTRGROUP_SERVICE)
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 分页查询属性分组
     */
    @GetMapping(ServiceUris.ProductAttrGroup.PAGE)
    public R<PageData<AttrGroupVO>> list(AttrGroupQueryDTO query, @PathVariable Long catelogId) {
        return R.ok(attrGroupService.page(query, catelogId));
    }

    /**
     * 查询属性分组详情
     */
    @GetMapping(ServiceUris.ProductAttrGroup.INFO)
    public R<AttrGroupVO> info(@PathVariable Long attrGroupId) {
        return R.ok(attrGroupService.getById(attrGroupId));
    }

    /**
     * 保存属性分组
     */
    @PostMapping(ServiceUris.ProductAttrGroup.SAVE)
    public R<Void> save(@RequestBody AttrGroupVO vo) {
        attrGroupService.save(vo);
        return R.ok();
    }

    /**
     * 更新属性分组
     */
    @PostMapping(ServiceUris.ProductAttrGroup.UPDATE)
    public R<Void> update(@PathVariable Long id, @RequestBody AttrGroupVO vo) {
        attrGroupService.updateById(id, vo);
        return R.ok();
    }

    /**
     * 删除属性分组
     */
    @PostMapping(ServiceUris.ProductAttrGroup.DELETE)
    public R<Void> delete(@PathVariable Long id) {
        attrGroupService.removeById(id);
        return R.ok();
    }

    /**
     * 查询分组及关联属性
     */
    @GetMapping(ServiceUris.ProductAttrGroup.WITH_ATTRS)
    public R<List<AttrGroupWithAttrsVo>> withAttrs(@PathVariable Long catelogId) {
        return R.ok(attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId));
    }
}
