package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.CouponSpuRelationEntity;
import com.youhaowu.coupon.mapper.CouponSpuRelationMapper;
import com.youhaowu.coupon.dto.CouponSpuRelationQueryDTO;
import com.youhaowu.coupon.service.CouponSpuRelationService;
import com.youhaowu.coupon.vo.CouponSpuRelationVO;
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
public class CouponSpuRelationServiceImpl implements CouponSpuRelationService {

    @Autowired
    private CouponSpuRelationMapper couponSpuRelationMapper;

    @Override
    public PageData<CouponSpuRelationVO> page(CouponSpuRelationQueryDTO query) {
        LambdaQueryWrapper<CouponSpuRelationEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(couponSpuRelationMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public CouponSpuRelationVO getById(Long id) {
        CouponSpuRelationEntity e = couponSpuRelationMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(CouponSpuRelationVO vo) {
        CouponSpuRelationEntity e = new CouponSpuRelationEntity();
        BeanUtil.copyProperties(vo, e);
        return couponSpuRelationMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<CouponSpuRelationVO> list) {
        List<CouponSpuRelationEntity> entities = list.stream().map(vo -> {
            CouponSpuRelationEntity e = new CouponSpuRelationEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return couponSpuRelationMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, CouponSpuRelationVO vo) {
        CouponSpuRelationEntity e = new CouponSpuRelationEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return couponSpuRelationMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return couponSpuRelationMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return couponSpuRelationMapper.deleteByIds(ids);
    }

    private CouponSpuRelationVO entityToVO(CouponSpuRelationEntity e) {
        CouponSpuRelationVO v = new CouponSpuRelationVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
