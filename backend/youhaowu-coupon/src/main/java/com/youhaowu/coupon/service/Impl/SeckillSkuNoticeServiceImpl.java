package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.SeckillSkuNoticeEntity;
import com.youhaowu.coupon.mapper.SeckillSkuNoticeMapper;
import com.youhaowu.coupon.dto.SeckillSkuNoticeQueryDTO;
import com.youhaowu.coupon.service.SeckillSkuNoticeService;
import com.youhaowu.coupon.vo.SeckillSkuNoticeVO;
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
public class SeckillSkuNoticeServiceImpl implements SeckillSkuNoticeService {

    @Autowired
    private SeckillSkuNoticeMapper seckillSkuNoticeMapper;

    @Override
    public PageData<SeckillSkuNoticeVO> page(SeckillSkuNoticeQueryDTO query) {
        LambdaQueryWrapper<SeckillSkuNoticeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(seckillSkuNoticeMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public SeckillSkuNoticeVO getById(Long id) {
        SeckillSkuNoticeEntity e = seckillSkuNoticeMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(SeckillSkuNoticeVO vo) {
        SeckillSkuNoticeEntity e = new SeckillSkuNoticeEntity();
        BeanUtil.copyProperties(vo, e);
        return seckillSkuNoticeMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<SeckillSkuNoticeVO> list) {
        List<SeckillSkuNoticeEntity> entities = list.stream().map(vo -> {
            SeckillSkuNoticeEntity e = new SeckillSkuNoticeEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return seckillSkuNoticeMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, SeckillSkuNoticeVO vo) {
        SeckillSkuNoticeEntity e = new SeckillSkuNoticeEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return seckillSkuNoticeMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return seckillSkuNoticeMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return seckillSkuNoticeMapper.deleteBatchIds(ids);
    }

    private SeckillSkuNoticeVO entityToVO(SeckillSkuNoticeEntity e) {
        SeckillSkuNoticeVO v = new SeckillSkuNoticeVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
