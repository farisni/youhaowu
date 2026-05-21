package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.product.entity.CategoryEntity;
import com.wheatmall.product.mapper.CategoryMapper;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> listWithTree() {
        // 1. 查出所有分类，转 VO
        List<CategoryEntity> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>().orderByAsc(CategoryEntity::getSort));
        List<CategoryVO> allVOs = allCategories.stream()
                .map(this::entityToVO)
                .collect(Collectors.toList());

        // 2. 按 parentCid 分组（一次遍历，O(n)）
        Map<Long, List<CategoryVO>> parentMap = allVOs.stream()
                .collect(Collectors.groupingBy(
                        vo -> vo.getParentCid() != null ? vo.getParentCid() : 0L));

        // 3. 遍历所有 VO，直接从 Map 取子节点组装（无递归，O(n)）
        for (CategoryVO vo : allVOs) {
            List<CategoryVO> children = parentMap.get(vo.getCatId());
            if (children != null && !children.isEmpty()) {
                children.sort(Comparator.comparing(CategoryVO::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
                vo.setChildren(children);
            }
        }

        // 4. 返回一级分类
        List<CategoryVO> level1 = parentMap.get(0L);
        return level1 != null ? level1 : new ArrayList<>();
    }

    private CategoryVO entityToVO(CategoryEntity entity) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
