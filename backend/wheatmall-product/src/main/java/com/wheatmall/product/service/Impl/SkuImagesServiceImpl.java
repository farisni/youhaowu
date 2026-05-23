package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SkuImagesVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SkuImagesEntity;
import com.wheatmall.product.mapper.SkuImagesMapper;
import com.wheatmall.product.service.SkuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * SkuImages Service 实现
 */
@Service
public class SkuImagesServiceImpl implements SkuImagesService {

    @Autowired
    private SkuImagesMapper skuImagesMapper;
    @Override
    public PageData<SkuImagesVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(skuImagesMapper, new LambdaQueryWrapper<>(), query, e -> {
        SkuImagesVO vo = new SkuImagesVO();
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SkuImagesEntity getById(Long id) {
        return skuImagesMapper.selectById(id);
    }

    @Override
    public void save(SkuImagesEntity entity) {
        skuImagesMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<SkuImagesEntity> list) {
        for (SkuImagesEntity e : list) {
            skuImagesMapper.insert(e);
        }
    }

    @Override
    public void updateById(SkuImagesEntity entity) {
        skuImagesMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        skuImagesMapper.deleteBatchIds(ids);
    }
}
