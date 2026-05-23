package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.entity.BrandEntity;
import com.wheatmall.product.mapper.BrandMapper;
import com.wheatmall.product.query.BrandQueryDTO;
import com.wheatmall.product.service.BrandService;
import com.wheatmall.product.service.CategoryBrandRelationService;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.product.vo.BrandVo;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageData<BrandVo> page(BrandQueryDTO query) {
        return PageUtils.selectPage(brandMapper, new LambdaQueryWrapper<>(), query, e -> {
            BrandVo v = new BrandVo();
            BeanUtils.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public BrandVo getById(Long id) {
        BrandEntity e = brandMapper.selectById(id);
        BrandVo v = new BrandVo();
        BeanUtils.copyProperties(e, v);
        return v;
    }

    @Override
    public BrandVo getVOById(Long id) {
        return getById(id);
    }

    @Override
    public void save(BrandVo vo) {
        BrandEntity e = new BrandEntity();
        BeanUtils.copyProperties(vo, e);
        brandMapper.insert(e);
    }

    @Override
    public void saveBatch(List<BrandVo> list) {
        for (BrandVo vo : list) {
            save(vo);
        }
    }

    @Override
    public void updateById(Long id, BrandVo vo) {
        BrandEntity e = new BrandEntity();
        BeanUtils.copyProperties(vo, e);
        e.setBrandId(id);
        brandMapper.updateById(e);
    }

    @Override
    public void removeById(Long id) {
        brandMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDetail(Long id, BrandVo vo) {
        BrandEntity entity = new BrandEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setBrandId(id);
        brandMapper.updateById(entity);
        if (StrUtil.isNotBlank(entity.getName())) {
            categoryBrandRelationService.updateBrand(id, entity.getName());
        }
    }
}
