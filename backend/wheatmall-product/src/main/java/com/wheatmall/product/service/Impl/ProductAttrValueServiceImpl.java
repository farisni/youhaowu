package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.ProductAttrValueEntity;
import com.wheatmall.product.mapper.ProductAttrValueMapper;
import com.wheatmall.product.service.ProductAttrValueService;
import com.wheatmall.product.vo.ProductAttrValueVO;
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
    public ProductAttrValueEntity getById(Long id) {
        return attrValueMapper.selectById(id);
    }

    @Override
    public Integer save(ProductAttrValueEntity entity) {
        return attrValueMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<ProductAttrValueEntity> list) {
        attrValueMapper.insert(list);
    }

    @Override
    public Integer updateById(ProductAttrValueEntity entity) {
        return attrValueMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        attrValueMapper.deleteBatchIds(ids);
    }

    @Override
    public void saveProductAttr(List<ProductAttrValueEntity> collect) {
        attrValueMapper.insert(collect);
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
