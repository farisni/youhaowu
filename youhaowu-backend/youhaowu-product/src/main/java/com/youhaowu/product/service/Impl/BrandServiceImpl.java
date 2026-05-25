package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.entity.BrandEntity;
import com.youhaowu.product.mapper.BrandMapper;
import com.youhaowu.product.query.BrandQueryDTO;
import com.youhaowu.product.service.BrandService;
import com.youhaowu.product.service.CategoryBrandRelationService;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.product.vo.BrandVO;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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
            BeanUtil.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public BrandVO getById(Long id) {
        BrandEntity e = brandMapper.selectById(id);
        BrandVO v = new BrandVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }

    @Override
    public BrandVO getVOById(Long id) {
        return getById(id);
    }

    @Override
    public Integer save(BrandVO vo) {
        BrandEntity e = new BrandEntity();
        BeanUtil.copyProperties(vo, e);
        return brandMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<BrandVO> list) {
        List<BrandEntity> entities = list.stream().map(vo -> {
            BrandEntity e = new BrandEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return brandMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, BrandVO vo) {
        BrandEntity e = new BrandEntity();
        BeanUtil.copyProperties(vo, e);
        e.setBrandId(id);
        return brandMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return brandMapper.deleteById(id);
    }

    @Override
    public void updateDetail(Long id, BrandVO vo) {
        BrandEntity entity = new BrandEntity();
        BeanUtil.copyProperties(vo, entity);
        entity.setBrandId(id);
        brandMapper.updateById(entity);
        if (StrUtil.isNotBlank(entity.getName())) {
            categoryBrandRelationService.updateBrand(id, entity.getName());
        }
    }
}
