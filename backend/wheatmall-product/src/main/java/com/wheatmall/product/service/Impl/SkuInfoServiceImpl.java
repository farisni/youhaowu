package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.entity.SkuInfoEntity;
import com.wheatmall.product.mapper.SkuInfoMapper;
import com.wheatmall.product.query.SkuQueryDTO;
import com.wheatmall.product.service.SkuInfoService;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.product.vo.SkuInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuInfoServiceImpl implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Override
    public PageData<SkuInfoVO> page(SkuQueryDTO query) {
        return PageUtils.selectPage(skuInfoMapper, new LambdaQueryWrapper<>(), query, e -> {
            SkuInfoVO v = new SkuInfoVO();
            BeanUtils.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public SkuInfoVO getVOById(Long id) {
        SkuInfoEntity e = skuInfoMapper.selectById(id);
        SkuInfoVO v = new SkuInfoVO();
        BeanUtils.copyProperties(e, v);
        return v;
    }

    @Override
    public void saveSkuInfo(SkuInfoVO vo) {
        SkuInfoEntity entity = new SkuInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        skuInfoMapper.insert(entity);
    }

    @Override
    public List<SkuInfoVO> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> entities = skuInfoMapper.selectList(
                new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSpuId, spuId));
        return entities.stream().map(e -> {
            SkuInfoVO v = new SkuInfoVO();
            BeanUtils.copyProperties(e, v);
            return v;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SkuInfoVO> listBySpuId(Long spuId) {
        return getSkusBySpuId(spuId);
    }
}
