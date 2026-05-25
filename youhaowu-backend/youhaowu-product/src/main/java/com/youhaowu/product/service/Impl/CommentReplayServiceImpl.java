package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.CommentReplayVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.CommentReplayEntity;
import com.youhaowu.product.mapper.CommentReplayMapper;
import com.youhaowu.product.service.CommentReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
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
    public CommentReplayVO getById(Long id) {
        CommentReplayEntity e = commentReplayMapper.selectById(id);
        if (e == null) return null;
        CommentReplayVO vo = new CommentReplayVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
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
    public Integer removeByIds(List<Long> ids) {
        return commentReplayMapper.deleteByIds(ids);
    }
}
