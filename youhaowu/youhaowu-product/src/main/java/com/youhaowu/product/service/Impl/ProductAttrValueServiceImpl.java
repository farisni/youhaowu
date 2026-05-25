package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.ProductAttrValueEntity;
import com.youhaowu.product.mapper.ProductAttrValueMapper;
import com.youhaowu.product.service.ProductAttrValueService;
import com.youhaowu.product.vo.ProductAttrValueVO;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProductAttrValueServiceImpl implements ProductAttrValueService {

    @Autowired
    private ProductAttrValueMapper attrValueMapper;

    @Override
    public PageData<ProductAttrValueVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(attrValueMapper, new LambdaQueryWrapper<>(), query, this::entityToVO);
    }

    @Override
    public ProductAttrValueVO getById(Long id) {
        ProductAttrValueEntity e = attrValueMapper.selectById(id);
        if (e == null) return null;
        ProductAttrValueVO vo = new ProductAttrValueVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }

    @Override
    public Integer save(ProductAttrValueVO vo) {
        ProductAttrValueEntity e = new ProductAttrValueEntity();
        BeanUtil.copyProperties(vo, e);
        return attrValueMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<ProductAttrValueVO> list) {
        List<ProductAttrValueEntity> entities = list.stream().map(vo -> {
            ProductAttrValueEntity e = new ProductAttrValueEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return attrValueMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(ProductAttrValueVO vo) {
        ProductAttrValueEntity e = new ProductAttrValueEntity();
        BeanUtil.copyProperties(vo, e);
        return attrValueMapper.updateById(e);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return attrValueMapper.deleteByIds(ids);
    }

    @Override
    public Integer saveProductAttr(List<ProductAttrValueVO> collect) {
        List<ProductAttrValueEntity> entities = collect.stream().map(vo -> {
            ProductAttrValueEntity e = new ProductAttrValueEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return attrValueMapper.insert(entities).size();
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrListforspu(Long spuId) {
        return attrValueMapper.selectList(
                new LambdaQueryWrapper<ProductAttrValueEntity>().eq(ProductAttrValueEntity::getSpuId, spuId));
    }

    @Override
    public List<ProductAttrValueVO> listBySpuId(Long spuId) {
        return baseAttrListforspu(spuId).stream().map(this::entityToVO).collect(Collectors.toList());
    }

    private ProductAttrValueVO entityToVO(ProductAttrValueEntity e) {
        ProductAttrValueVO vo = new ProductAttrValueVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }
}
