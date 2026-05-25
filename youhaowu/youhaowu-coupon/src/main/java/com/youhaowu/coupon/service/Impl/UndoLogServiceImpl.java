package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.UndoLogEntity;
import com.youhaowu.coupon.mapper.UndoLogMapper;
import com.youhaowu.coupon.dto.UndoLogQueryDTO;
import com.youhaowu.coupon.service.UndoLogService;
import com.youhaowu.coupon.vo.UndoLogVO;
import com.youhaowu.common.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class UndoLogServiceImpl implements UndoLogService {

    @Autowired
    private UndoLogMapper undoLogMapper;

    @Override
    public PageData<UndoLogVO> page(UndoLogQueryDTO query) {
        LambdaQueryWrapper<UndoLogEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(undoLogMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public UndoLogVO getById(Long id) {
        UndoLogEntity e = undoLogMapper.selectById(id);
        return entityToVO(e);
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
    public Integer updateById(Long id, UndoLogVO vo) {
        UndoLogEntity e = new UndoLogEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return undoLogMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return undoLogMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return undoLogMapper.deleteByIds(ids);
    }

    private UndoLogVO entityToVO(UndoLogEntity e) {
        UndoLogVO v = new UndoLogVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
