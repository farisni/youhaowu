package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SkuImagesVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.SkuImagesEntity;
import com.youhaowu.product.mapper.SkuImagesMapper;
import com.youhaowu.product.service.SkuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
/**
 * SkuImages Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SkuImagesServiceImpl implements SkuImagesService {

    @Autowired
    private SkuImagesMapper skuImagesMapper;
    @Override
    public PageData<SkuImagesVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(skuImagesMapper, new LambdaQueryWrapper<>(), query, e -> {
        SkuImagesVO vo = new SkuImagesVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SkuImagesVO getById(Long id) {
        SkuImagesEntity e = skuImagesMapper.selectById(id);
        if (e == null) return null;
        SkuImagesVO vo = new SkuImagesVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }

    @Override
    public Integer save(SkuImagesVO vo) {
        SkuImagesEntity e = new SkuImagesEntity();
        BeanUtil.copyProperties(vo, e);
        return skuImagesMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SkuImagesVO> list) {
        List<SkuImagesEntity> entities = list.stream().map(vo -> {
            SkuImagesEntity e = new SkuImagesEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return skuImagesMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(SkuImagesVO vo) {
        SkuImagesEntity e = new SkuImagesEntity();
        BeanUtil.copyProperties(vo, e);
        return skuImagesMapper.updateById(e);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return skuImagesMapper.deleteBatchIds(ids);
    }
}
