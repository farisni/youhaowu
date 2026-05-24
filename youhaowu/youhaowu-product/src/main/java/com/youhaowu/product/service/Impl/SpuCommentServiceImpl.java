package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.SpuCommentVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.SpuCommentEntity;
import com.youhaowu.product.mapper.SpuCommentMapper;
import com.youhaowu.product.service.SpuCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
/**
 * SpuComment Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SpuCommentServiceImpl implements SpuCommentService {

    @Autowired
    private SpuCommentMapper spuCommentMapper;
    @Override
    public PageData<SpuCommentVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(spuCommentMapper, new LambdaQueryWrapper<>(), query, e -> {
        SpuCommentVO vo = new SpuCommentVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SpuCommentVO getById(Long id) {
        SpuCommentEntity e = spuCommentMapper.selectById(id);
        if (e == null) return null;
        SpuCommentVO vo = new SpuCommentVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
    }

    @Override
    public Integer save(SpuCommentVO vo) {
        SpuCommentEntity e = new SpuCommentEntity();
        BeanUtil.copyProperties(vo, e);
        return spuCommentMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SpuCommentVO> list) {
        List<SpuCommentEntity> entities = list.stream().map(vo -> {
            SpuCommentEntity e = new SpuCommentEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return spuCommentMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(SpuCommentVO vo) {
        SpuCommentEntity e = new SpuCommentEntity();
        BeanUtil.copyProperties(vo, e);
        return spuCommentMapper.updateById(e);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return spuCommentMapper.deleteBatchIds(ids);
    }
}
