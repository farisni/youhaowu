package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.entity.AttrGroupEntity;
import com.wheatmall.product.mapper.AttrGroupMapper;
import com.wheatmall.product.query.AttrGroupQueryDTO;
import com.wheatmall.product.service.AttrGroupService;
import com.wheatmall.product.service.AttrService;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.product.vo.AttrGroupVO;
import com.wheatmall.product.vo.AttrGroupWithAttrsVO;
import org.springframework.beans.BeanUtils;
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
            BeanUtils.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public AttrGroupVO getById(Long id) {
        AttrGroupEntity e = attrGroupMapper.selectById(id);
        AttrGroupVO v = new AttrGroupVO();
        BeanUtils.copyProperties(e, v);
        return v;
    }

    @Override
    public AttrGroupVO getVOById(Long id) {
        return getById(id);
    }

    @Override
    public Integer save(AttrGroupVO vo) {
        AttrGroupEntity e = new AttrGroupEntity();
        BeanUtils.copyProperties(vo, e);
        return attrGroupMapper.insert(e);
    }

    @Override
    public void saveBatch(List<AttrGroupVO> list) {
        for (AttrGroupVO vo : list) {
            save(vo);
        }
    }

    @Override
    public Integer updateById(Long id, AttrGroupVO vo) {
        AttrGroupEntity e = new AttrGroupEntity();
        BeanUtils.copyProperties(vo, e);
        e.setAttrGroupId(id);
        return attrGroupMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return attrGroupMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        attrGroupMapper.deleteBatchIds(ids);
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        List<AttrGroupEntity> groups = attrGroupMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>().eq(AttrGroupEntity::getCatelogId, catelogId));
        return groups.stream().map(group -> {
            AttrGroupWithAttrsVO vo = new AttrGroupWithAttrsVO();
            BeanUtils.copyProperties(group, vo);
            vo.setAttrs(attrService.getRelationAttr(vo.getAttrGroupId()));
            return vo;
        }).collect(Collectors.toList());
    }
}
