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

import java.util.List;
/**
 * UndoLog Service 实现
 */
@Service
public class UndoLogServiceImpl implements UndoLogService {

    @Autowired
    private UndoLogMapper undoLogMapper;
    @Override
    public PageData<UndoLogVO> page(BaseQueryDTO query) {
        return PageUtils.selectPage(undoLogMapper, new LambdaQueryWrapper<>(), query, e -> {
        UndoLogVO vo = new UndoLogVO();
        org.springframework.beans.BeanUtils.copyProperties(e, vo);
        return vo;
    });
    }

    @Override
    public UndoLogEntity getById(Long id) {
        return undoLogMapper.selectById(id);
    }

    @Override
    public Integer save(UndoLogEntity entity) {
        return undoLogMapper.insert(entity);
    }

    @Override
    public void saveBatch(List<UndoLogEntity> list) {
        for (UndoLogEntity e : list) {
            undoLogMapper.insert(e);
        }
    }

    @Override
    public Integer updateById(UndoLogEntity entity) {
        return undoLogMapper.updateById(entity);
    }

    @Override
    public void removeByIds(List<Long> ids) {
        undoLogMapper.deleteBatchIds(ids);
    }
}
