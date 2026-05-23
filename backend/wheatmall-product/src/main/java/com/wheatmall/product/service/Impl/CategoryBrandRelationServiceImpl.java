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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            BeanUtils.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public CategoryBrandRelationVO getById(Long id) {
        CategoryBrandRelationEntity e = categoryBrandRelationMapper.selectById(id);
        CategoryBrandRelationVO v = new CategoryBrandRelationVO();
        BeanUtils.copyProperties(e, v);
        return v;
    }

    @Override
    public CategoryBrandRelationVO getVOById(Long id) {
        return getById(id);
    }

    @Override
    public void save(CategoryBrandRelationVO vo) {
        CategoryBrandRelationEntity e = new CategoryBrandRelationEntity();
        BeanUtils.copyProperties(vo, e);
        categoryBrandRelationMapper.insert(e);
    }

    @Override
    public void saveBatch(List<CategoryBrandRelationVO> list) {
        for (CategoryBrandRelationVO vo : list) {
            save(vo);
        }
    }

    @Override
    public void updateById(CategoryBrandRelationVO vo) {
        CategoryBrandRelationEntity e = new CategoryBrandRelationEntity();
        BeanUtils.copyProperties(vo, e);
        categoryBrandRelationMapper.updateById(e);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        categoryBrandRelationMapper.deleteBatchIds(ids);
    }

    @Override
    public void saveDetail(CategoryBrandRelationVO vo) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        BeanUtils.copyProperties(vo, entity);
        BrandEntity brand = brandMapper.selectById(entity.getBrandId());
        CategoryEntity category = categoryMapper.selectById(entity.getCatelogId());
        entity.setBrandName(brand != null ? brand.getName() : "");
        entity.setCatelogName(category != null ? category.getName() : "");
        categoryBrandRelationMapper.insert(entity);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(name);
        categoryBrandRelationMapper.update(entity,
                new LambdaUpdateWrapper<CategoryBrandRelationEntity>()
                        .eq(CategoryBrandRelationEntity::getBrandId, brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        categoryBrandRelationMapper.updateCategory(catId, name);
    }
}
