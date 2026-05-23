package com.wheatmall.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.coupon.entity.SeckillSessionEntity;
import com.wheatmall.coupon.mapper.SeckillSessionMapper;
import com.wheatmall.coupon.dto.SeckillSessionQueryDTO;
import com.wheatmall.coupon.service.SeckillSessionService;
import com.wheatmall.coupon.vo.SeckillSessionVO;
import com.wheatmall.common.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class SeckillSessionServiceImpl implements SeckillSessionService {

    @Autowired
    private SeckillSessionMapper seckillSessionMapper;

    @Override
    public PageData<SeckillSessionVO> page(SeckillSessionQueryDTO query) {
        LambdaQueryWrapper<SeckillSessionEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(seckillSessionMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SeckillSessionVO getById(Long id) {
        SeckillSessionEntity e = seckillSessionMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SeckillSessionVO vo) {
        SeckillSessionEntity e = new SeckillSessionEntity();
        BeanUtil.copyProperties(vo, e);
        return seckillSessionMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SeckillSessionVO> list) {
        List<SeckillSessionEntity> entities = list.stream().map(vo -> {
            SeckillSessionEntity e = new SeckillSessionEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return seckillSessionMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SeckillSessionVO vo) {
        SeckillSessionEntity e = new SeckillSessionEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return seckillSessionMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return seckillSessionMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return seckillSessionMapper.deleteBatchIds(ids);
    }

    private SeckillSessionVO entityToVO(SeckillSessionEntity e) {
        SeckillSessionVO v = new SeckillSessionVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
