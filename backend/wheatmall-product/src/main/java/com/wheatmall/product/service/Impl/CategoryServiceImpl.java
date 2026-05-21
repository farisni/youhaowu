package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.product.entity.CategoryEntity;
import com.wheatmall.product.mapper.CategoryMapper;
import com.wheatmall.product.service.CategoryService;
import com.wheatmall.product.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> listWithTree() {
        List<CategoryEntity> allCategories = categoryMapper.selectList(
                new LambdaQueryWrapper<CategoryEntity>().orderByAsc(CategoryEntity::getSort));
        List<CategoryVO> level1List = allCategories.stream()
                .filter(cat -> cat.getParentCid() == 0)
                .map(this::entityToVO)
                .collect(Collectors.toList());
        for (CategoryVO vo : level1List) {
            vo.setChildren(getChildren(vo.getCatId(), allCategories));
        }
        return level1List;
    }

    private List<CategoryVO> getChildren(Long parentId, List<CategoryEntity> allCategories) {
        List<CategoryVO> children = allCategories.stream()
                .filter(cat -> cat.getParentCid().equals(parentId))
                .map(this::entityToVO)
                .collect(Collectors.toList());
        for (CategoryVO child : children) {
            child.setChildren(getChildren(child.getCatId(), allCategories));
        }
        return children.isEmpty() ? null : children;
    }

    private CategoryVO entityToVO(CategoryEntity entity) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
