package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.constant.ProductConstant;
import com.wheatmall.product.entity.AttrAttrgroupRelationEntity;
import com.wheatmall.product.entity.AttrEntity;
import com.wheatmall.product.entity.AttrGroupEntity;
import com.wheatmall.product.entity.CategoryEntity;
import com.wheatmall.product.mapper.AttrAttrgroupRelationMapper;
import com.wheatmall.product.mapper.AttrGroupMapper;
import com.wheatmall.product.mapper.AttrMapper;
import com.wheatmall.product.mapper.CategoryMapper;
import com.wheatmall.product.query.AttrQueryDTO;
import com.wheatmall.product.service.AttrService;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.product.vo.AttrRespVO;
import com.wheatmall.product.vo.AttrVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Attr Service 实现
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private AttrAttrgroupRelationMapper relationMapper;

    @Autowired
    private AttrGroupMapper attrGroupMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageData<AttrRespVO> page(AttrQueryDTO query) {
        return PageUtils.selectPage(attrMapper, new LambdaQueryWrapper<>(), query, e -> {
            AttrRespVO vo = new AttrRespVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        });
    }

    @Override
    public AttrRespVO getAttrInfo(Long attrId) {
        AttrEntity attr = attrMapper.selectById(attrId);
        if (attr == null) return null;
        AttrRespVO vo = new AttrRespVO();
        BeanUtils.copyProperties(attr, vo);
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity rel = relationMapper.selectOne(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
            if (rel != null) {
                vo.setAttrGroupId(rel.getAttrGroupId());
                AttrGroupEntity grp = attrGroupMapper.selectById(rel.getAttrGroupId());
                if (grp != null) vo.setGroupName(grp.getAttrGroupName());
            }
        }
        vo.setCatelogPath(findCatelogPath(attr.getCatelogId()));
        CategoryEntity cat = categoryMapper.selectById(attr.getCatelogId());
        if (cat != null) vo.setCatelogName(cat.getName());
        return vo;
    }

    @Override
    public AttrRespVO getVOById(Long id) {
        return getAttrInfo(id);
    }

    @Override
    public Integer saveAttr(AttrVO vo) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(vo, entity);
        return attrMapper.insert(entity);
    }

    @Override
    public Integer save(AttrVO vo) {
        return saveAttr(vo);
    }

    @Override
    public void saveBatch(List<AttrVO> list) {
        for (AttrVO vo : list) {
            saveAttr(vo);
        }
    }

    @Override
    public Integer removeById(Long id) {
        return attrMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        attrMapper.deleteBatchIds(ids);
    }

    @Override
    public PageData<AttrRespVO> queryBaseAttrPage(AttrQueryDTO query, Long catelogId, String attrType) {
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getAttrType, "base".equalsIgnoreCase(attrType)
                        ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0) wrapper.eq(AttrEntity::getCatelogId, catelogId);

        Page<AttrEntity> page = PageUtils.buildPage(query);
        var result = attrMapper.selectPage(page, wrapper);
        List<AttrRespVO> vos = result.getRecords().stream().map(attr -> {
            AttrRespVO vo = new AttrRespVO();
            BeanUtils.copyProperties(attr, vo);
            if ("base".equalsIgnoreCase(attrType)) {
                AttrAttrgroupRelationEntity rel = relationMapper.selectOne(
                        new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId()));
                if (rel != null && rel.getAttrGroupId() != null) {
                    AttrGroupEntity grp = attrGroupMapper.selectById(rel.getAttrGroupId());
                    if (grp != null) vo.setGroupName(grp.getAttrGroupName());
                }
            }
            CategoryEntity cat = categoryMapper.selectById(attr.getCatelogId());
            if (cat != null) vo.setCatelogName(cat.getName());
            return vo;
        }).collect(Collectors.toList());
        return PageData.of(result.getTotal(), result.getSize(), result.getCurrent(), vos);
    }

    @Override
    public List<AttrRespVO> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> entities = relationMapper.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrgroupId));
        List<Long> attrIds = entities.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());
        if (attrIds.isEmpty()) return Collections.emptyList();
        List<AttrEntity> attrs = attrMapper.selectBatchIds(attrIds);
        return attrs.stream().map(a -> {
            AttrRespVO v = new AttrRespVO();
            BeanUtils.copyProperties(a, v);
            return v;
        }).collect(Collectors.toList());
    }

    @Override
    public PageData<?> getNoRelationAttr(AttrQueryDTO query, Long attrgroupId) {
        AttrGroupEntity attrGroup = attrGroupMapper.selectById(attrgroupId);
        if (attrGroup == null) return PageData.of(0, query.getPageSize(), 1, List.of());
        Long catelogId = attrGroup.getCatelogId();
        List<AttrGroupEntity> groups = attrGroupMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getCatelogId, catelogId));
        List<Long> groupIds = groups.stream()
                .map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> relations = relationMapper.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .in(AttrAttrgroupRelationEntity::getAttrGroupId, groupIds));
        List<Long> attrIds = relations.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getCatelogId, catelogId)
                .eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (!attrIds.isEmpty()) wrapper.notIn(AttrEntity::getAttrId, attrIds);

        return PageUtils.selectPage(attrMapper, wrapper, query, e -> {
            AttrRespVO vo = new AttrRespVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAttrById(Long id, AttrVO attr) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(attr, entity);
        entity.setAttrId(id);
        attrMapper.updateById(entity);
        if (entity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                && attr.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity rel = new AttrAttrgroupRelationEntity();
            rel.setAttrGroupId(attr.getAttrGroupId());
            rel.setAttrId(id);
            Long count = relationMapper.selectCount(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq(AttrAttrgroupRelationEntity::getAttrId, id));
            if (count > 0) {
                relationMapper.update(rel, new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrId, id));
            } else {
                relationMapper.insert(rel);
            }
        }
    }

    private Long[] findCatelogPath(Long catelogId) {
        if (catelogId == null || catelogId == 0) return new Long[0];
        CategoryEntity cat = categoryMapper.selectById(catelogId);
        if (cat == null) return new Long[]{catelogId};
        Long[] parent = findCatelogPath(cat.getParentCid());
        Long[] result = new Long[parent.length + 1];
        System.arraycopy(parent, 0, result, 0, parent.length);
        result[result.length - 1] = catelogId;
        return result;
    }
}
