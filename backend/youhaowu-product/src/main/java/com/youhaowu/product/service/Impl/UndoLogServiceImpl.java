package com.youhaowu.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.product.vo.UndoLogVO;
import com.youhaowu.product.utils.PageUtils;
import com.youhaowu.common.dto.BaseQueryDTO;
import com.youhaowu.product.entity.UndoLogEntity;
import com.youhaowu.product.mapper.UndoLogMapper;
import com.youhaowu.product.service.UndoLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;
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
    public UndoLogVO getById(Long id) {
        UndoLogEntity e = undoLogMapper.selectById(id);
        if (e == null) return null;
        UndoLogVO vo = new UndoLogVO();
        BeanUtil.copyProperties(e, vo);
        return vo;
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
    public Integer removeByIds(List<Long> ids) {
        return undoLogMapper.deleteBatchIds(ids);
    }
}
