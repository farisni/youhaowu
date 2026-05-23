package com.wheatmall.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.entity.MemberReceiveAddressEntity;
import com.wheatmall.member.mapper.MemberReceiveAddressMapper;
import com.wheatmall.member.dto.MemberReceiveAddressQueryDTO;
import com.wheatmall.member.service.MemberReceiveAddressService;
import com.wheatmall.member.vo.MemberReceiveAddressVO;
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
public class MemberReceiveAddressServiceImpl implements MemberReceiveAddressService {

    @Autowired
    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    @Override
    public PageData<MemberReceiveAddressVO> page(MemberReceiveAddressQueryDTO query) {
        LambdaQueryWrapper<MemberReceiveAddressEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberReceiveAddressMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberReceiveAddressVO getById(Long id) {
        MemberReceiveAddressEntity e = memberReceiveAddressMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberReceiveAddressVO vo) {
        MemberReceiveAddressEntity e = new MemberReceiveAddressEntity();
        BeanUtil.copyProperties(vo, e);
        return memberReceiveAddressMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberReceiveAddressVO> list) {
        List<MemberReceiveAddressEntity> entities = list.stream().map(vo -> {
            MemberReceiveAddressEntity e = new MemberReceiveAddressEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberReceiveAddressMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberReceiveAddressVO vo) {
        MemberReceiveAddressEntity e = new MemberReceiveAddressEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberReceiveAddressMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberReceiveAddressMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberReceiveAddressMapper.deleteBatchIds(ids);
    }

    private MemberReceiveAddressVO entityToVO(MemberReceiveAddressEntity e) {
        MemberReceiveAddressVO v = new MemberReceiveAddressVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
