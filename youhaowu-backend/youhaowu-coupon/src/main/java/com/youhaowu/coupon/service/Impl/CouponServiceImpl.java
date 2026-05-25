package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.CouponEntity;
import com.youhaowu.coupon.mapper.CouponMapper;
import com.youhaowu.coupon.dto.CouponQueryDTO;
import com.youhaowu.coupon.service.CouponService;
import com.youhaowu.coupon.vo.CouponVO;
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
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public PageData<CouponVO> page(CouponQueryDTO query) {
        LambdaQueryWrapper<CouponEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(couponMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public CouponVO getById(Long id) {
        CouponEntity e = couponMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(CouponVO vo) {
        CouponEntity e = new CouponEntity();
        BeanUtil.copyProperties(vo, e);
        return couponMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<CouponVO> list) {
        List<CouponEntity> entities = list.stream().map(vo -> {
            CouponEntity e = new CouponEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return couponMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, CouponVO vo) {
        CouponEntity e = new CouponEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return couponMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return couponMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return couponMapper.deleteByIds(ids);
    }

    private CouponVO entityToVO(CouponEntity e) {
        CouponVO v = new CouponVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
