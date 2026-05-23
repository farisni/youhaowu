package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuSaleAttrValueVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SkuSaleAttrValueEntity;
import com.wheatmall.product.mapper.SkuSaleAttrValueMapper;
import com.wheatmall.product.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * SkuSaleAttrValue Service 实现
 */
@Service
public class SkuSaleAttrValueServiceImpl implements SkuSaleAttrValueService {

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    @Override
    public PageData<SkuSaleAttrValueVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(skuSaleAttrValueMapper, new LambdaQueryWrapper<>(), query, e -> {
        SkuSaleAttrValueVO vo = new SkuSaleAttrValueVO();
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SkuSaleAttrValueEntity getById(Long id) {
        return skuSaleAttrValueMapper.selectById(id);
    }

    @Override
    public void save(SkuSaleAttrValueEntity entity) {
        skuSaleAttrValueMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<SkuSaleAttrValueEntity> list) {
        for (SkuSaleAttrValueEntity e : list) {
            skuSaleAttrValueMapper.insert(e);
        }
    }

    @Override
    public void updateById(SkuSaleAttrValueEntity entity) {
        skuSaleAttrValueMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        skuSaleAttrValueMapper.deleteBatchIds(ids);
    }
}
