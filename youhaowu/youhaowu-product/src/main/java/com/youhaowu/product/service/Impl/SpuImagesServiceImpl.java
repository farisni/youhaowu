package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.product.mapper.SpuImagesMapper;
import com.youhaowu.product.entity.SpuImagesEntity;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SpuImagesVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.service.SpuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
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
    public SpuImagesVO getById(Long id) {
        SpuImagesEntity e = spuImagesMapper.selectById(id);
        if (e == null) return null;
        SpuImagesVO vo = new SpuImagesVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
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
    public Integer removeByIds(List<Long> ids) {
        return spuImagesMapper.deleteBatchIds(ids);
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
