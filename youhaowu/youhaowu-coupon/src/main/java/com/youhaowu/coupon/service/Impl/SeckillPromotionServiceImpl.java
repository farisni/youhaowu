package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.SeckillPromotionEntity;
import com.youhaowu.coupon.mapper.SeckillPromotionMapper;
import com.youhaowu.coupon.dto.SeckillPromotionQueryDTO;
import com.youhaowu.coupon.service.SeckillPromotionService;
import com.youhaowu.coupon.vo.SeckillPromotionVO;
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
public class SeckillPromotionServiceImpl implements SeckillPromotionService {

    @Autowired
    private SeckillPromotionMapper seckillPromotionMapper;

    @Override
    public PageData<SeckillPromotionVO> page(SeckillPromotionQueryDTO query) {
        LambdaQueryWrapper<SeckillPromotionEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(seckillPromotionMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SeckillPromotionVO getById(Long id) {
        SeckillPromotionEntity e = seckillPromotionMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SeckillPromotionVO vo) {
        SeckillPromotionEntity e = new SeckillPromotionEntity();
        BeanUtil.copyProperties(vo, e);
        return seckillPromotionMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SeckillPromotionVO> list) {
        List<SeckillPromotionEntity> entities = list.stream().map(vo -> {
            SeckillPromotionEntity e = new SeckillPromotionEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return seckillPromotionMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SeckillPromotionVO vo) {
        SeckillPromotionEntity e = new SeckillPromotionEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return seckillPromotionMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return seckillPromotionMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return seckillPromotionMapper.deleteBatchIds(ids);
    }

    private SeckillPromotionVO entityToVO(SeckillPromotionEntity e) {
        SeckillPromotionVO v = new SeckillPromotionVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
