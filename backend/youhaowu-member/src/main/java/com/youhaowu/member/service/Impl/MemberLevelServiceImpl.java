package com.youhaowu.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.entity.MemberLevelEntity;
import com.youhaowu.member.mapper.MemberLevelMapper;
import com.youhaowu.member.dto.MemberLevelQueryDTO;
import com.youhaowu.member.service.MemberLevelService;
import com.youhaowu.member.vo.MemberLevelVO;
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
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    private MemberLevelMapper memberLevelMapper;

    @Override
    public PageData<MemberLevelVO> page(MemberLevelQueryDTO query) {
        LambdaQueryWrapper<MemberLevelEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberLevelMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberLevelVO getById(Long id) {
        MemberLevelEntity e = memberLevelMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberLevelVO vo) {
        MemberLevelEntity e = new MemberLevelEntity();
        BeanUtil.copyProperties(vo, e);
        return memberLevelMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberLevelVO> list) {
        List<MemberLevelEntity> entities = list.stream().map(vo -> {
            MemberLevelEntity e = new MemberLevelEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberLevelMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberLevelVO vo) {
        MemberLevelEntity e = new MemberLevelEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberLevelMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberLevelMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberLevelMapper.deleteBatchIds(ids);
    }

    private MemberLevelVO entityToVO(MemberLevelEntity e) {
        MemberLevelVO v = new MemberLevelVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
