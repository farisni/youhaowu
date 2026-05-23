package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuInfoDescVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SpuInfoDescEntity;
import com.wheatmall.product.mapper.SpuInfoDescMapper;
import com.wheatmall.product.service.SpuInfoDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * SpuInfoDesc Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SpuInfoDescServiceImpl implements SpuInfoDescService {

    @Autowired
    private SpuInfoDescMapper spuInfoDescMapper;
    @Override
    public PageData<SpuInfoDescVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(spuInfoDescMapper, new LambdaQueryWrapper<>(), query, e -> {
        SpuInfoDescVO vo = new SpuInfoDescVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SpuInfoDescEntity getById(Long id) {
        return spuInfoDescMapper.selectById(id);
    }

    @Override
    public Integer save(SpuInfoDescEntity entity) {
        return spuInfoDescMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<SpuInfoDescEntity> list) {
        spuInfoDescMapper.insert(list);
    }

    @Override
    public Integer updateById(SpuInfoDescEntity entity) {
        return spuInfoDescMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        spuInfoDescMapper.deleteBatchIds(ids);
    }
    @Override
    public void saveSpuInfoDesc(SpuInfoDescEntity descEntity) {
        spuInfoDescMapper.insert(descEntity);
    }
}
