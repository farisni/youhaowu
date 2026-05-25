package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.CouponHistoryEntity;
import com.youhaowu.coupon.mapper.CouponHistoryMapper;
import com.youhaowu.coupon.dto.CouponHistoryQueryDTO;
import com.youhaowu.coupon.service.CouponHistoryService;
import com.youhaowu.coupon.vo.CouponHistoryVO;
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
public class CouponHistoryServiceImpl implements CouponHistoryService {

    @Autowired
    private CouponHistoryMapper couponHistoryMapper;

    @Override
    public PageData<CouponHistoryVO> page(CouponHistoryQueryDTO query) {
        LambdaQueryWrapper<CouponHistoryEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(couponHistoryMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public CouponHistoryVO getById(Long id) {
        CouponHistoryEntity e = couponHistoryMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(CouponHistoryVO vo) {
        CouponHistoryEntity e = new CouponHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        return couponHistoryMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<CouponHistoryVO> list) {
        List<CouponHistoryEntity> entities = list.stream().map(vo -> {
            CouponHistoryEntity e = new CouponHistoryEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return couponHistoryMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, CouponHistoryVO vo) {
        CouponHistoryEntity e = new CouponHistoryEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return couponHistoryMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return couponHistoryMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return couponHistoryMapper.deleteByIds(ids);
    }

    private CouponHistoryVO entityToVO(CouponHistoryEntity e) {
        CouponHistoryVO v = new CouponHistoryVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
