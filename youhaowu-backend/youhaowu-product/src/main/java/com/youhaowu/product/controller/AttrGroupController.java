package com.youhaowu.product.controller;

import com.youhaowu.common.constant.ProductServiceUris;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.query.AttrGroupQueryDTO;
import com.youhaowu.product.service.AttrGroupService;
import com.youhaowu.product.vo.AttrGroupVO;
import com.youhaowu.product.vo.AttrGroupWithAttrsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性分组控制器
 */
@RestController
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 分页查询属性分组
     */
    @GetMapping(ProductServiceUris.ProductAttrGroup.PAGE)
    public PageData<AttrGroupVO> list(AttrGroupQueryDTO query, @PathVariable Long catelogId) {
        return attrGroupService.page(query, catelogId);
    }

    /**
     * 查询属性分组详情
     */
    @GetMapping(ProductServiceUris.ProductAttrGroup.INFO)
    public AttrGroupVO info(@PathVariable Long attrGroupId) {
        return attrGroupService.getById(attrGroupId);
    }

    /**
     * 保存属性分组
     */
    @PostMapping(ProductServiceUris.ProductAttrGroup.SAVE)
    public Integer save(@RequestBody AttrGroupVO vo) {
        return attrGroupService.save(vo);
    }

    /**
     * 更新属性分组
     */
    @PostMapping(ProductServiceUris.ProductAttrGroup.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody AttrGroupVO vo) {
        return attrGroupService.updateById(id, vo);
    }

    /**
     * 删除属性分组
     */
    @PostMapping(ProductServiceUris.ProductAttrGroup.DELETE)
    public Integer delete(@PathVariable Long id) {
        return attrGroupService.removeById(id);
    }

    /**
     * 查询分组及关联属性
     */
    @GetMapping(ProductServiceUris.ProductAttrGroup.WITH_ATTRS)
    public List<AttrGroupWithAttrsVO> withAttrs(@PathVariable Long catelogId) {
        return attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
    }
}
