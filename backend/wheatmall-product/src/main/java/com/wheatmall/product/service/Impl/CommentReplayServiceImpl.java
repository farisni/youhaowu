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
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
import org.apache.ibatis.executor.BatchResult;
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
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public CommentReplayEntity getById(Long id) {
        return commentReplayMapper.selectById(id);
    }

    @Override
    public Integer save(CommentReplayVO vo) {
        CommentReplayEntity e = new CommentReplayEntity();
        BeanUtil.copyProperties(vo, e);
        return commentReplayMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<CommentReplayVO> list) {
        List<CommentReplayEntity> entities = list.stream().map(vo -> {
            CommentReplayEntity e = new CommentReplayEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return commentReplayMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(CommentReplayVO vo) {
        CommentReplayEntity e = new CommentReplayEntity();
        BeanUtil.copyProperties(vo, e);
        return commentReplayMapper.updateById(e);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        commentReplayMapper.deleteBatchIds(ids);
    }
}
