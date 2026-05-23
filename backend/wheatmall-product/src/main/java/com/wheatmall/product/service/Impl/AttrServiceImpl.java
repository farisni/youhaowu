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
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.executor.BatchResult;

/**
 * Attr Service 实现
 */
@Transactional(rollbackFor = Exception.class)
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

    /**
     * 分页查询所有属性
     */
    @Override
    public PageData<AttrRespVO> page(AttrQueryDTO query) {
        return PageUtils.selectPage(attrMapper, new LambdaQueryWrapper<>(), query, e -> {
            AttrRespVO vo = new AttrRespVO();
            BeanUtil.copyProperties(e, vo);
            return vo;
        });
    }

    /**
     * 根据ID查询属性详情（含分组名、分类路径、分类名）
     */
    @Override
    public AttrRespVO getAttrInfo(Long attrId) {
        // 1. 查询属性基本信息
        AttrEntity attr = attrMapper.selectById(attrId);
        if (attr == null) return null;
        AttrRespVO vo = new AttrRespVO();
        BeanUtil.copyProperties(attr, vo);

        // 2. 基本属性：补充所属分组ID和分组名
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

        // 3. 补充分类路径和分类名
        vo.setCatelogPath(findCatelogPath(attr.getCatelogId()));
        CategoryEntity cat = categoryMapper.selectById(attr.getCatelogId());
        if (cat != null) vo.setCatelogName(cat.getName());
        return vo;
    }

    /**
     * 根据ID查询属性详情（同 getAttrInfo）
     */
    @Override
    public AttrRespVO getVOById(Long id) {
        return getAttrInfo(id);
    }

    /**
     * 新增属性
     */
    @Override
    public Integer saveAttr(AttrVO vo) {
        AttrEntity entity = new AttrEntity();
        BeanUtil.copyProperties(vo, entity);
        return attrMapper.insert(entity);
    }

    /**
     * 新增属性（同 saveAttr）
     */
    @Override
    public Integer save(AttrVO vo) {
        return saveAttr(vo);
    }

    /**
     * 批量新增属性
     */
    @Override
    public Integer saveBatch(List<AttrVO> list) {
        List<AttrEntity> entities = list.stream().map(vo -> {
            AttrEntity e = new AttrEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return attrMapper.insert(entities).size();
    }

    /**
     * 根据ID删除属性
     */
    @Override
    public Integer removeById(Long id) {
        return attrMapper.deleteById(id);
    }

    /**
     * 批量删除属性
     */
    @Override
    public Integer removeByIds(List<Long> ids) {
        return attrMapper.deleteBatchIds(ids);
    }

    /**
     * 分页查询基本属性或销售属性（带分组名和分类名）
     */
    @Override
    public PageData<AttrRespVO> queryBaseAttrPage(AttrQueryDTO query, Long catelogId, String attrType) {
        // 1. 构建查询条件：按类型和分类过滤
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getAttrType, "base".equalsIgnoreCase(attrType)
                        ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0) wrapper.eq(AttrEntity::getCatelogId, catelogId);

        // 2. 分页查询
        Page<AttrEntity> page = PageUtils.buildPage(query);
        var result = attrMapper.selectPage(page, wrapper);

        // 3. 转换为 VO，基本属性补充分组名，所有属性补充分类名
        List<AttrRespVO> vos = result.getRecords().stream().map(attr -> {
            AttrRespVO vo = new AttrRespVO();
            BeanUtil.copyProperties(attr, vo);
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

    /**
     * 查询指定分组已关联的属性列表
     */
    @Override
    public List<AttrRespVO> getRelationAttr(Long attrgroupId) {
        // 1. 查关联关系，取属性ID列表
        List<AttrAttrgroupRelationEntity> entities = relationMapper.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrgroupId));
        List<Long> attrIds = entities.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());
        if (attrIds.isEmpty()) return Collections.emptyList();

        // 2. 批量查询属性并转 VO
        List<AttrEntity> attrs = attrMapper.selectBatchIds(attrIds);
        return attrs.stream().map(a -> {
            AttrRespVO v = new AttrRespVO();
            BeanUtil.copyProperties(a, v);
            return v;
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询当前分组未关联的属性（同一分类下未被其他分组占用的属性）
     */
    @Override
    public PageData<?> getNoRelationAttr(AttrQueryDTO query, Long attrgroupId) {
        // 1. 查询分组信息，获取所属分类
        AttrGroupEntity attrGroup = attrGroupMapper.selectById(attrgroupId);
        if (attrGroup == null) return PageData.of(0, query.getPageSize(), 1, List.of());
        Long catelogId = attrGroup.getCatelogId();

        // 2. 查出该分类下所有属性分组
        List<AttrGroupEntity> groups = attrGroupMapper.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getCatelogId, catelogId));
        List<Long> groupIds = groups.stream()
                .map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        // 3. 查出这些分组已关联的所有属性ID
        List<AttrAttrgroupRelationEntity> relations = relationMapper.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .in(AttrAttrgroupRelationEntity::getAttrGroupId, groupIds));
        List<Long> attrIds = relations.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        // 4. 查询同分类下、类型为"基本属性"、且未被关联的属性
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getCatelogId, catelogId)
                .eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (!attrIds.isEmpty()) wrapper.notIn(AttrEntity::getAttrId, attrIds);

        return PageUtils.selectPage(attrMapper, wrapper, query, e -> {
            AttrRespVO vo = new AttrRespVO();
            BeanUtil.copyProperties(e, vo);
            return vo;
        });
    }

    /**
     * 更新属性（基本属性同步维护关联关系分组）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateAttrById(Long id, AttrVO attr) {
        // 1. 更新属性基本信息
        AttrEntity entity = new AttrEntity();
        BeanUtil.copyProperties(attr, entity);
        entity.setAttrId(id);
        int affected = attrMapper.updateById(entity);


        // 2. 基本属性：维护与分组的关联关系（已有则更新，无则新增）
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
        return affected;
    }

    /**
     * 递归查询分类ID的完整路径（从顶层到当前层）
     */
    private Long[] findCatelogPath(Long catelogId) {
        if (catelogId == null || catelogId == 0) return new Long[0];
        CategoryEntity cat = categoryMapper.selectById(catelogId);
        if (cat == null) return new Long[]{catelogId};
        // 递归查父级路径，拼接当前ID
        Long[] parent = findCatelogPath(cat.getParentCid());
        Long[] result = new Long[parent.length + 1];
        System.arraycopy(parent, 0, result, 0, parent.length);
        result[result.length - 1] = catelogId;
        return result;
    }
}
