package com.youhaowu.member.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.entity.MemberCollectSpuEntity;
import com.youhaowu.member.mapper.MemberCollectSpuMapper;
import com.youhaowu.member.dto.MemberCollectSpuQueryDTO;
import com.youhaowu.member.service.MemberCollectSpuService;
import com.youhaowu.member.vo.MemberCollectSpuVO;
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
public class MemberCollectSpuServiceImpl implements MemberCollectSpuService {

    @Autowired
    private MemberCollectSpuMapper memberCollectSpuMapper;

    @Override
    public PageData<MemberCollectSpuVO> page(MemberCollectSpuQueryDTO query) {
        LambdaQueryWrapper<MemberCollectSpuEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(memberCollectSpuMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberCollectSpuVO getById(Long id) {
        MemberCollectSpuEntity e = memberCollectSpuMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberCollectSpuVO vo) {
        MemberCollectSpuEntity e = new MemberCollectSpuEntity();
        BeanUtil.copyProperties(vo, e);
        return memberCollectSpuMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberCollectSpuVO> list) {
        List<MemberCollectSpuEntity> entities = list.stream().map(vo -> {
            MemberCollectSpuEntity e = new MemberCollectSpuEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberCollectSpuMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberCollectSpuVO vo) {
        MemberCollectSpuEntity e = new MemberCollectSpuEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberCollectSpuMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberCollectSpuMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberCollectSpuMapper.deleteByIds(ids);
    }

    private MemberCollectSpuVO entityToVO(MemberCollectSpuEntity e) {
        MemberCollectSpuVO v = new MemberCollectSpuVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
