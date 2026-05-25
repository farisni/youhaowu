package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.entity.AttrGroupEntity;
import com.youhaowu.product.mapper.AttrGroupMapper;
import com.youhaowu.product.query.AttrGroupQueryDTO;
import com.youhaowu.product.service.AttrGroupService;
import com.youhaowu.product.service.AttrService;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.product.vo.AttrGroupVO;
import com.youhaowu.product.vo.AttrGroupWithAttrsVO;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class AttrGroupServiceImpl implements AttrGroupService {

    @Autowired
    private AttrGroupMapper attrGroupMapper;

    @Autowired
    private AttrService attrService;

    @Override
    public PageData<AttrGroupVO> page(AttrGroupQueryDTO query, Long catelogId) {
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        if (catelogId != null && catelogId != 0) {
            wrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
        }
        return PageUtils.selectPage(attrGroupMapper, wrapper, query, e -> {
            AttrGroupVO v = new AttrGroupVO();
            BeanUtil.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public AttrGroupVO getById(Long id) {
        AttrGroupEntity e = attrGroupMapper.selectById(id);
        AttrGroupVO v = new AttrGroupVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }

    @Override
    public AttrGroupVO getVOById(Long id) {
        return getById(id);
    }

    @Override
    public Integer save(AttrGroupVO vo) {
        AttrGroupEntity e = new AttrGroupEntity();
        BeanUtil.copyProperties(vo, e);
        return attrGroupMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<AttrGroupVO> list) {
        List<AttrGroupEntity> entities = list.stream().map(vo -> {
            AttrGroupEntity e = new AttrGroupEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(java.util.stream.Collectors.toList());
        return attrGroupMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, AttrGroupVO vo) {
        AttrGroupEntity e = new AttrGroupEntity();
        BeanUtil.copyProperties(vo, e);
        e.setAttrGroupId(id);
        return attrGroupMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return attrGroupMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return attrGroupMapper.deleteByIds(ids);
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        List<AttrGroupEntity> groups = attrGroupMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>().eq(AttrGroupEntity::getCatelogId, catelogId));
        return groups.stream().map(group -> {
            AttrGroupWithAttrsVO vo = new AttrGroupWithAttrsVO();
            BeanUtil.copyProperties(group, vo);
            vo.setAttrs(attrService.getRelationAttr(vo.getAttrGroupId()));
            return vo;
        }).collect(Collectors.toList());
    }
}
