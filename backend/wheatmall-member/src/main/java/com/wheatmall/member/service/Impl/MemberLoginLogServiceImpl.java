package com.wheatmall.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.entity.MemberLoginLogEntity;
import com.wheatmall.member.mapper.MemberLoginLogMapper;
import com.wheatmall.member.dto.MemberLoginLogQueryDTO;
import com.wheatmall.member.service.MemberLoginLogService;
import com.wheatmall.member.vo.MemberLoginLogVO;
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
public class MemberLoginLogServiceImpl implements MemberLoginLogService {

    @Autowired
    private MemberLoginLogMapper memberLoginLogMapper;

    @Override
    public PageData<MemberLoginLogVO> page(MemberLoginLogQueryDTO query) {
        LambdaQueryWrapper<MemberLoginLogEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberLoginLogMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberLoginLogVO getById(Long id) {
        MemberLoginLogEntity e = memberLoginLogMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberLoginLogVO vo) {
        MemberLoginLogEntity e = new MemberLoginLogEntity();
        BeanUtil.copyProperties(vo, e);
        return memberLoginLogMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberLoginLogVO> list) {
        List<MemberLoginLogEntity> entities = list.stream().map(vo -> {
            MemberLoginLogEntity e = new MemberLoginLogEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberLoginLogMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberLoginLogVO vo) {
        MemberLoginLogEntity e = new MemberLoginLogEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberLoginLogMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberLoginLogMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberLoginLogMapper.deleteBatchIds(ids);
    }

    private MemberLoginLogVO entityToVO(MemberLoginLogEntity e) {
        MemberLoginLogVO v = new MemberLoginLogVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
