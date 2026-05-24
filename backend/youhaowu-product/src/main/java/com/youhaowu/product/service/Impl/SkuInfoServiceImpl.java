package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.entity.SkuInfoEntity;
import com.youhaowu.product.mapper.SkuInfoMapper;
import com.youhaowu.product.query.SkuQueryDTO;
import com.youhaowu.product.service.SkuInfoService;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.product.vo.SkuInfoVO;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class SkuInfoServiceImpl implements SkuInfoService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Override
    public PageData<SkuInfoVO> page(SkuQueryDTO query) {
        return PageUtils.selectPage(skuInfoMapper, new LambdaQueryWrapper<>(), query, e -> {
            SkuInfoVO v = new SkuInfoVO();
            BeanUtil.copyProperties(e, v);
            return v;
        });
    }

    @Override
    public SkuInfoVO getVOById(Long id) {
        SkuInfoEntity e = skuInfoMapper.selectById(id);
        SkuInfoVO v = new SkuInfoVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }

    @Override
    public Integer saveSkuInfo(SkuInfoVO vo) {
        SkuInfoEntity entity = new SkuInfoEntity();
        BeanUtil.copyProperties(vo, entity);
        return skuInfoMapper.insert(entity);
    }

    @Override
    public List<SkuInfoVO> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> entities = skuInfoMapper.selectList(
                new LambdaQueryWrapper<SkuInfoEntity>().eq(SkuInfoEntity::getSpuId, spuId));
        return entities.stream().map(e -> {
            SkuInfoVO v = new SkuInfoVO();
            BeanUtil.copyProperties(e, v);
            return v;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SkuInfoVO> listBySpuId(Long spuId) {
        return getSkusBySpuId(spuId);
    }
}
