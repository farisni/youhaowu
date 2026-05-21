package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.PageUtils;
import com.wheatmall.product.dto.CategoryQueryDTO;
import com.wheatmall.product.entity.CategoryEntity;
import com.wheatmall.product.mapper.CategoryMapper;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> listWithTree() {
        List<CategoryEntity> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>().orderByAsc(CategoryEntity::getSort));
        List<CategoryVO> allVOs = allCategories.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());

        Map<Long, List<CategoryVO>> parentMap = allVOs.stream()
                .collect(Collectors.groupingBy(
                        vo -> vo.getParentCid() != null ? vo.getParentCid() : 0L));

        for (CategoryVO vo : allVOs) {
            List<CategoryVO> children = parentMap.get(vo.getCatId());
            if (children != null && !children.isEmpty()) {
                children.sort(Comparator.comparing(CategoryVO::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
                vo.setChildren(children);
            }
        }

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
        PageUtils.applySort(wrapper, query, CategoryEntity::getSort);
        Page<CategoryEntity> result = categoryMapper.selectPage(PageUtils.buildPage(query), wrapper);

        List<CategoryVO> voList = result.getRecords().stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());

        return PageData.of(result.getTotal(), result.getSize(), result.getCurrent(), voList);
    }

    private CategoryVO entityToVO(CategoryEntity entity) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
