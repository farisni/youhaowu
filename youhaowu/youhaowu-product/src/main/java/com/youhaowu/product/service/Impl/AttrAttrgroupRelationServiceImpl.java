package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.product.vo.AttrGroupRelationVO;
import cn.hutool.core.bean.BeanUtil;
import java.util.stream.Collectors;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.AttrAttrgroupRelationVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.AttrAttrgroupRelationEntity;
import com.youhaowu.product.mapper.AttrAttrgroupRelationMapper;
import com.youhaowu.product.service.AttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * AttrAttrgroupRelation Service 实现
 */
@Transactional(rollbackFor = Exception.class)
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
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public AttrAttrgroupRelationVO getById(Long id) {
        AttrAttrgroupRelationEntity e = attrAttrgroupRelationMapper.selectById(id);
        if (e == null) return null;
        AttrAttrgroupRelationVO vo = new AttrAttrgroupRelationVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }

    @Override
    public Integer save(AttrAttrgroupRelationVO vo) {
        AttrAttrgroupRelationEntity e = new AttrAttrgroupRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return attrAttrgroupRelationMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<AttrAttrgroupRelationVO> list) {
        List<AttrAttrgroupRelationEntity> entities = list.stream().map(vo -> {
            AttrAttrgroupRelationEntity e = new AttrAttrgroupRelationEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return attrAttrgroupRelationMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(AttrAttrgroupRelationVO vo) {
        AttrAttrgroupRelationEntity e = new AttrAttrgroupRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return attrAttrgroupRelationMapper.updateById(e);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return attrAttrgroupRelationMapper.deleteByIds(ids);
    }
    @Override
    public Integer saveRelationBatch(List<AttrGroupRelationVO> vos) {
        List<AttrAttrgroupRelationEntity> list = vos.stream().map(item -> {
            AttrAttrgroupRelationEntity e = new AttrAttrgroupRelationEntity();
            BeanUtil.copyProperties(item, e);
            return e;
        }).collect(Collectors.toList());
        return relationMapper.insert(list).size();
    }
}
