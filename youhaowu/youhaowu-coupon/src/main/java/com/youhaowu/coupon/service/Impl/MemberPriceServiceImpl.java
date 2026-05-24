package com.youhaowu.coupon.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.coupon.entity.MemberPriceEntity;
import com.youhaowu.coupon.mapper.MemberPriceMapper;
import com.youhaowu.coupon.dto.MemberPriceQueryDTO;
import com.youhaowu.coupon.service.MemberPriceService;
import com.youhaowu.coupon.vo.MemberPriceVO;
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
public class MemberPriceServiceImpl implements MemberPriceService {

    @Autowired
    private MemberPriceMapper memberPriceMapper;

    @Override
    public PageData<MemberPriceVO> page(MemberPriceQueryDTO query) {
        LambdaQueryWrapper<MemberPriceEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberPriceMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberPriceVO getById(Long id) {
        MemberPriceEntity e = memberPriceMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberPriceVO vo) {
        MemberPriceEntity e = new MemberPriceEntity();
        BeanUtil.copyProperties(vo, e);
        return memberPriceMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberPriceVO> list) {
        List<MemberPriceEntity> entities = list.stream().map(vo -> {
            MemberPriceEntity e = new MemberPriceEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberPriceMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberPriceVO vo) {
        MemberPriceEntity e = new MemberPriceEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberPriceMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberPriceMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberPriceMapper.deleteBatchIds(ids);
    }

    private MemberPriceVO entityToVO(MemberPriceEntity e) {
        MemberPriceVO v = new MemberPriceVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
