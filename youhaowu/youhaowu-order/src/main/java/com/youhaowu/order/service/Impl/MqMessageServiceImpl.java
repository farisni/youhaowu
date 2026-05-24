package com.youhaowu.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.entity.MqMessageEntity;
import com.youhaowu.order.mapper.MqMessageMapper;
import com.youhaowu.order.dto.MqMessageQueryDTO;
import com.youhaowu.order.service.MqMessageService;
import com.youhaowu.order.vo.MqMessageVO;
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
public class MqMessageServiceImpl implements MqMessageService {

    @Autowired
    private MqMessageMapper mqMessageMapper;

    @Override
    public PageData<MqMessageVO> page(MqMessageQueryDTO query) {
        LambdaQueryWrapper<MqMessageEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  MqMessage 无排序字段，关键字搜索按需扩展
        }
        return PageUtils.selectPage(mqMessageMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MqMessageVO getById(Long id) {
        MqMessageEntity e = mqMessageMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MqMessageVO vo) {
        MqMessageEntity e = new MqMessageEntity();
        BeanUtil.copyProperties(vo, e);
        return mqMessageMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MqMessageVO> list) {
        List<MqMessageEntity> entities = list.stream().map(vo -> {
            MqMessageEntity e = new MqMessageEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return mqMessageMapper.insert(entities).size();
    }

    //  MqMessage 主键为 String messageId，不支持通用 updateById


    //  MqMessage 主键为 String messageId，不支持通用 removeById/removeByIds


    private MqMessageVO entityToVO(MqMessageEntity e) {
        MqMessageVO v = new MqMessageVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
