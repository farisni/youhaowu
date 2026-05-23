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
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SpuImagesEntity getById(Long id) {
        return spuImagesMapper.selectById(id);
    }

    @Override
    public Integer save(SpuImagesEntity entity) {
        return spuImagesMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<SpuImagesEntity> list) {
        for (SpuImagesEntity e : list) {
            spuImagesMapper.insert(e);
        }
    }

    @Override
    public Integer updateById(SpuImagesEntity entity) {
        return spuImagesMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        spuImagesMapper.deleteBatchIds(ids);
    }
    @Override
    public void saveImages(Long spuId, List<String> images) {
        if (images != null && !images.isEmpty()) {
            for (String img : images) {
                SpuImagesEntity e = new SpuImagesEntity();
                e.setSpuId(spuId);
                e.setImgUrl(img);
                spuImagesMapper.insert(e);
            }
        }
    }
}
