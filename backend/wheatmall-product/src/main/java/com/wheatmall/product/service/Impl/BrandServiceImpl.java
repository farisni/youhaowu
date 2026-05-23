package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.entity.BrandEntity;
import com.wheatmall.product.mapper.BrandMapper;
import com.wheatmall.product.query.BrandQueryDTO;
import com.wheatmall.product.service.BrandService;
import com.wheatmall.product.service.CategoryBrandRelationService;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.product.vo.BrandVO;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageData<BrandVO> page(BrandQueryDTO query) {
        return PageUtils.selectPage(brandMapper, new LambdaQueryWrapper<>(), query, e -> {
            BrandVO v = new BrandVO();
            BeanUtils.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public BrandVO getById(Long id) {
        BrandEntity e = brandMapper.selectById(id);
        BrandVO v = new BrandVO();
        BeanUtils.copyProperties(e, v);
        return v;
    }

    @Override
    public BrandVO getVOById(Long id) {
        return getById(id);
    }

    @Override
    public Integer save(BrandVO vo) {
        BrandEntity e = new BrandEntity();
        BeanUtils.copyProperties(vo, e);
        return brandMapper.insert(e);
    }

    @Override
    public void saveBatch(List<BrandVO> list) {
        for (BrandVO vo : list) {
            save(vo);
        }
    }

    @Override
    public Integer updateById(Long id, BrandVO vo) {
        BrandEntity e = new BrandEntity();
        BeanUtils.copyProperties(vo, e);
        e.setBrandId(id);
        return brandMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return brandMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDetail(Long id, BrandVO vo) {
        BrandEntity entity = new BrandEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setBrandId(id);
        brandMapper.updateById(entity);
        if (StrUtil.isNotBlank(entity.getName())) {
            categoryBrandRelationService.updateBrand(id, entity.getName());
        }
    }
}
