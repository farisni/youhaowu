package com.wheatmall.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.product.vo.UndoLogVO;
import com.wheatmall.product.utils.PageUtils;
import com.wheatmall.common.dto.BaseQueryDTO;
import com.wheatmall.product.entity.UndoLogEntity;
import com.wheatmall.product.mapper.UndoLogMapper;
import com.wheatmall.product.service.UndoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
import org.apache.ibatis.executor.BatchResult;
/**
 * UndoLog Service 实现
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UndoLogServiceImpl implements UndoLogService {

    @Autowired
    private UndoLogMapper undoLogMapper;
    @Override
    public PageData<UndoLogVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(undoLogMapper, new LambdaQueryWrapper<>(), query, e -> {
        UndoLogVO vo = new UndoLogVO();
        cn.hutool.core.bean.BeanUtil.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public UndoLogEntity getById(Long id) {
        return undoLogMapper.selectById(id);
    }

    @Override
    public Integer save(UndoLogVO vo) {
        UndoLogEntity e = new UndoLogEntity();
        BeanUtil.copyProperties(vo, e);
        return undoLogMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<UndoLogVO> list) {
        List<UndoLogEntity> entities = list.stream().map(vo -> {
            UndoLogEntity e = new UndoLogEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return undoLogMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(UndoLogVO vo) {
        UndoLogEntity e = new UndoLogEntity();
        BeanUtil.copyProperties(vo, e);
        return undoLogMapper.updateById(e);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        undoLogMapper.deleteBatchIds(ids);
    }
}
