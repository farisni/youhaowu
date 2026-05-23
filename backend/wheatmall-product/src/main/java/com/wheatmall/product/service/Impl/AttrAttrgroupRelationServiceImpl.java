package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.product.vo.AttrGroupRelationVO;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.AttrAttrgroupRelationVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.AttrAttrgroupRelationEntity;
import com.wheatmall.product.mapper.AttrAttrgroupRelationMapper;
import com.wheatmall.product.service.AttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * AttrAttrgroupRelation Service 实现
 */
@Service
public class AttrAttrgroupRelationServiceImpl implements AttrAttrgroupRelationService {

    @Autowired
    private AttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    @Autowired
    private AttrAttrgroupRelationMapper relationMapper;
    @Override
    public PageData<AttrAttrgroupRelationVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(attrAttrgroupRelationMapper, new LambdaQueryWrapper<>(), query, e -> {
        AttrAttrgroupRelationVO vo = new AttrAttrgroupRelationVO();
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public AttrAttrgroupRelationEntity getById(Long id) {
        return attrAttrgroupRelationMapper.selectById(id);
    }

    @Override
    public Integer save(AttrAttrgroupRelationEntity entity) {
        return attrAttrgroupRelationMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<AttrAttrgroupRelationEntity> list) {
        for (AttrAttrgroupRelationEntity e : list) {
            attrAttrgroupRelationMapper.insert(e);
        }
    }

    @Override
    public Integer updateById(AttrAttrgroupRelationEntity entity) {
        return attrAttrgroupRelationMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        attrAttrgroupRelationMapper.deleteBatchIds(ids);
    }
    @Override
    public void saveRelationBatch(List<AttrGroupRelationVO> vos) {
        List<AttrAttrgroupRelationEntity> list = vos.stream().map(item -> {
            AttrAttrgroupRelationEntity e = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, e);
            return e;
        }).collect(Collectors.toList());
        for (AttrAttrgroupRelationEntity e : list) {
            relationMapper.insert(e);
        }
    }
}
