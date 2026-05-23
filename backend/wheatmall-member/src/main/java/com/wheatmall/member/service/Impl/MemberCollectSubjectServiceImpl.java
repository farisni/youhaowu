package com.wheatmall.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.member.entity.MemberCollectSubjectEntity;
import com.wheatmall.member.mapper.MemberCollectSubjectMapper;
import com.wheatmall.member.dto.MemberCollectSubjectQueryDTO;
import com.wheatmall.member.service.MemberCollectSubjectService;
import com.wheatmall.member.vo.MemberCollectSubjectVO;
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
public class MemberCollectSubjectServiceImpl implements MemberCollectSubjectService {

    @Autowired
    private MemberCollectSubjectMapper memberCollectSubjectMapper;

    @Override
    public PageData<MemberCollectSubjectVO> page(MemberCollectSubjectQueryDTO query) {
        LambdaQueryWrapper<MemberCollectSubjectEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberCollectSubjectMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberCollectSubjectVO getById(Long id) {
        MemberCollectSubjectEntity e = memberCollectSubjectMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberCollectSubjectVO vo) {
        MemberCollectSubjectEntity e = new MemberCollectSubjectEntity();
        BeanUtil.copyProperties(vo, e);
        return memberCollectSubjectMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberCollectSubjectVO> list) {
        List<MemberCollectSubjectEntity> entities = list.stream().map(vo -> {
            MemberCollectSubjectEntity e = new MemberCollectSubjectEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberCollectSubjectMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberCollectSubjectVO vo) {
        MemberCollectSubjectEntity e = new MemberCollectSubjectEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberCollectSubjectMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberCollectSubjectMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberCollectSubjectMapper.deleteBatchIds(ids);
    }

    private MemberCollectSubjectVO entityToVO(MemberCollectSubjectEntity e) {
        MemberCollectSubjectVO v = new MemberCollectSubjectVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
