package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.entity.BrandEntity;
import com.wheatmall.product.entity.CategoryBrandRelationEntity;
import com.wheatmall.product.entity.CategoryEntity;
import com.wheatmall.product.mapper.BrandMapper;
import com.wheatmall.product.mapper.CategoryBrandRelationMapper;
import com.wheatmall.product.mapper.CategoryMapper;
import com.wheatmall.product.service.CategoryBrandRelationService;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.product.vo.CategoryBrandRelationVO;
import cn.hutool.core.bean.BeanUtil;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class CategoryBrandRelationServiceImpl implements CategoryBrandRelationService {

    @Autowired
    private CategoryBrandRelationMapper categoryBrandRelationMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageData<CategoryBrandRelationVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(categoryBrandRelationMapper, new LambdaQueryWrapper<>(), query, e -> {
            CategoryBrandRelationVO v = new CategoryBrandRelationVO();
            BeanUtil.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public CategoryBrandRelationVO getById(Long id) {
        CategoryBrandRelationEntity e = categoryBrandRelationMapper.selectById(id);
        CategoryBrandRelationVO v = new CategoryBrandRelationVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }

    @Override
    public CategoryBrandRelationVO getVOById(Long id) {
        return getById(id);
    }

    @Override
    public Integer save(CategoryBrandRelationVO vo) {
        CategoryBrandRelationEntity e = new CategoryBrandRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return categoryBrandRelationMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<CategoryBrandRelationVO> list) {
        List<CategoryBrandRelationEntity> entities = list.stream().map(vo -> {
            CategoryBrandRelationEntity e = new CategoryBrandRelationEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(java.util.stream.Collectors.toList());
        return categoryBrandRelationMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(CategoryBrandRelationVO vo) {
        CategoryBrandRelationEntity e = new CategoryBrandRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return categoryBrandRelationMapper.updateById(e);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return categoryBrandRelationMapper.deleteBatchIds(ids);
    }

    @Override
    public Integer saveDetail(CategoryBrandRelationVO vo) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        BeanUtil.copyProperties(vo, entity);
        BrandEntity brand = brandMapper.selectById(entity.getBrandId());
        CategoryEntity category = categoryMapper.selectById(entity.getCatelogId());
        entity.setBrandName(brand != null ? brand.getName() : "");
        entity.setCatelogName(category != null ? category.getName() : "");
        return categoryBrandRelationMapper.insert(entity);
    }

    @Override
    public Integer updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(name);
        return categoryBrandRelationMapper.update(entity,
                new LambdaUpdateWrapper<CategoryBrandRelationEntity>()
                        .eq(CategoryBrandRelationEntity::getBrandId, brandId));
    }

    @Override
    public Integer updateCategory(Long catId, String name) {
        return categoryBrandRelationMapper.updateCategory(catId, name);
    }
}
