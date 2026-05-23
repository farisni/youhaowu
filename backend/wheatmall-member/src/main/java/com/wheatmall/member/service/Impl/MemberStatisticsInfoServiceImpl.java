package com.wheatmall.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.entity.MemberStatisticsInfoEntity;
import com.wheatmall.member.mapper.MemberStatisticsInfoMapper;
import com.wheatmall.member.dto.MemberStatisticsInfoQueryDTO;
import com.wheatmall.member.service.MemberStatisticsInfoService;
import com.wheatmall.member.vo.MemberStatisticsInfoVO;
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
public class MemberStatisticsInfoServiceImpl implements MemberStatisticsInfoService {

    @Autowired
    private MemberStatisticsInfoMapper memberStatisticsInfoMapper;

    @Override
    public PageData<MemberStatisticsInfoVO> page(MemberStatisticsInfoQueryDTO query) {
        LambdaQueryWrapper<MemberStatisticsInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberStatisticsInfoMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberStatisticsInfoVO getById(Long id) {
        MemberStatisticsInfoEntity e = memberStatisticsInfoMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberStatisticsInfoVO vo) {
        MemberStatisticsInfoEntity e = new MemberStatisticsInfoEntity();
        BeanUtil.copyProperties(vo, e);
        return memberStatisticsInfoMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberStatisticsInfoVO> list) {
        List<MemberStatisticsInfoEntity> entities = list.stream().map(vo -> {
            MemberStatisticsInfoEntity e = new MemberStatisticsInfoEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberStatisticsInfoMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberStatisticsInfoVO vo) {
        MemberStatisticsInfoEntity e = new MemberStatisticsInfoEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberStatisticsInfoMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberStatisticsInfoMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberStatisticsInfoMapper.deleteBatchIds(ids);
    }

    private MemberStatisticsInfoVO entityToVO(MemberStatisticsInfoEntity e) {
        MemberStatisticsInfoVO v = new MemberStatisticsInfoVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
