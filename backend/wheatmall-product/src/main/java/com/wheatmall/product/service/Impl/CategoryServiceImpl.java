package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.dto.CategoryQueryDTO;
import com.wheatmall.product.dto.CategoryUpdateDTO;
import com.wheatmall.product.entity.CategoryEntity;
import com.wheatmall.product.mapper.CategoryMapper;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取三级分类树（非递归 O(n) 实现）
     * 一级分类嵌二级，二级嵌三级
     */
    @Override
    public List<CategoryVO> listWithTree() {
        // 1. 查询所有分类，按 sort 排序后转为 VO
        List<CategoryEntity> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>().orderByAsc(CategoryEntity::getSort));
        List<CategoryVO> allVOs = allCategories.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());

        // 2. 按 parentCid 分组
        Map<Long, List<CategoryVO>> parentMap = allVOs.stream()
                .collect(Collectors.groupingBy(
                        vo -> vo.getParentCid() != null ? vo.getParentCid() : 0L));

        // 3. 将子分类挂到父分类上，子分类内部按 sort 排序
        for (CategoryVO vo : allVOs) {
            List<CategoryVO> children = parentMap.get(vo.getCatId());
            if (children != null && !children.isEmpty()) {
                children.sort(Comparator.comparing(CategoryVO::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
                vo.setChildren(children);
            }
        }

        // 4. 返回一级分类（parentCid=0）
        List<CategoryVO> level1 = parentMap.get(0L);
        return level1 != null ? level1 : new ArrayList<>();
    }

    @Override
    public PageData<CategoryVO> page(CategoryQueryDTO query) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getName())) {
            wrapper.like(CategoryEntity::getName, query.getName());
        }
        if (StrUtil.isNotBlank(query.getKeyword())) {
            wrapper.and(w -> w.like(CategoryEntity::getName, query.getKeyword()));
        }
        return PageUtils.selectPage(categoryMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public List<CategoryVO> getChildrenByParentId(Long parentId) {
        List<CategoryEntity> entities = categoryMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>()
                        .eq(CategoryEntity::getParentCid, parentId)
                        .orderByAsc(CategoryEntity::getSort));
        return entities.stream().map(this::entityToVO).collect(Collectors.toList());
    }

    @Override
    public Integer deleteBatch(List<Long> ids) {
        return categoryMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer update(CategoryUpdateDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        BeanUtil.copyProperties(dto, entity);
        return categoryMapper.updateById(entity);
    }

    private CategoryVO entityToVO(CategoryEntity entity) {
        CategoryVO vo = new CategoryVO();
        BeanUtil.copyProperties(entity, vo);
        return vo;
    }
}
