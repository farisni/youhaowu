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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
import org.apache.ibatis.executor.BatchResult;
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
    public SkuImagesEntity getById(Long id) {
        return skuImagesMapper.selectById(id);
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
    public void removeByIds(List<Long> ids) {
        skuImagesMapper.deleteBatchIds(ids);
    }
}
