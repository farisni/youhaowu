package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.CommentReplayVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.CommentReplayEntity;
import com.wheatmall.product.mapper.CommentReplayMapper;
import com.wheatmall.product.service.CommentReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * CommentReplay Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class CommentReplayServiceImpl implements CommentReplayService {

    @Autowired
    private CommentReplayMapper commentReplayMapper;

    @Override
    public PageData<CommentReplayVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(commentReplayMapper, new LambdaQueryWrapper<>(), query, e -> {
        CommentReplayVO vo = new CommentReplayVO();
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public CommentReplayEntity getById(Long id) {
        return commentReplayMapper.selectById(id);
    }

    @Override
    public Integer save(CommentReplayEntity entity) {
        return commentReplayMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<CommentReplayEntity> list) {
        commentReplayMapper.insert(list);
    }

    @Override
    public Integer updateById(CommentReplayEntity entity) {
        return commentReplayMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        commentReplayMapper.deleteBatchIds(ids);
    }
}
