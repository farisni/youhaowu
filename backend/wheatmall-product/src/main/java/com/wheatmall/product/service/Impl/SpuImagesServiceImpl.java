package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.product.mapper.SpuImagesMapper;
import com.wheatmall.product.entity.SpuImagesEntity;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuImagesVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SpuImagesEntity;
import com.wheatmall.product.mapper.SpuImagesMapper;
import com.wheatmall.product.service.SpuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
import org.apache.ibatis.executor.BatchResult;
/**
 * SpuImages Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SpuImagesServiceImpl implements SpuImagesService {

    @Autowired
    private SpuImagesMapper spuImagesMapper;
    @Override
    public PageData<SpuImagesVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(spuImagesMapper, new LambdaQueryWrapper<>(), query, e -> {
        SpuImagesVO vo = new SpuImagesVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SpuImagesEntity getById(Long id) {
        return spuImagesMapper.selectById(id);
    }

    @Override
    public Integer save(SpuImagesVO vo) {
        SpuImagesEntity e = new SpuImagesEntity();
        BeanUtil.copyProperties(vo, e);
        return spuImagesMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SpuImagesVO> list) {
        List<SpuImagesEntity> entities = list.stream().map(vo -> {
            SpuImagesEntity e = new SpuImagesEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return spuImagesMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(SpuImagesVO vo) {
        SpuImagesEntity e = new SpuImagesEntity();
        BeanUtil.copyProperties(vo, e);
        return spuImagesMapper.updateById(e);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        spuImagesMapper.deleteBatchIds(ids);
    }
    @Override
    public Integer saveImages(Long spuId, List<String> images) {
        if (images != null && !images.isEmpty()) {
            List<SpuImagesEntity> entities = images.stream().map(img -> {
                SpuImagesEntity e = new SpuImagesEntity();
                e.setSpuId(spuId);
                e.setImgUrl(img);
                return e;
            }).collect(Collectors.toList());
            return spuImagesMapper.insert(entities).size();
        }
        return 0;
    }
}
