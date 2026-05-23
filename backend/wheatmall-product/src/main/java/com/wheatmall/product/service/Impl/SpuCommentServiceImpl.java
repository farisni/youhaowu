package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.SpuCommentVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.SpuCommentEntity;
import com.wheatmall.product.mapper.SpuCommentMapper;
import com.wheatmall.product.service.SpuCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * SpuComment Service 实现
 */
@Service
public class SpuCommentServiceImpl implements SpuCommentService {

    @Autowired
    private SpuCommentMapper spuCommentMapper;
    @Override
    public PageData<SpuCommentVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(spuCommentMapper, new LambdaQueryWrapper<>(), query, e -> {
        SpuCommentVO vo = new SpuCommentVO();
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public SpuCommentEntity getById(Long id) {
        return spuCommentMapper.selectById(id);
    }

    @Override
    public void save(SpuCommentEntity entity) {
        spuCommentMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<SpuCommentEntity> list) {
        for (SpuCommentEntity e : list) {
            spuCommentMapper.insert(e);
        }
    }

    @Override
    public void updateById(SpuCommentEntity entity) {
        spuCommentMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        spuCommentMapper.deleteBatchIds(ids);
    }
}
