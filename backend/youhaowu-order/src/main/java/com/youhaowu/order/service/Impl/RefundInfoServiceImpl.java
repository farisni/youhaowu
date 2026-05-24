package com.youhaowu.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.entity.RefundInfoEntity;
import com.youhaowu.order.mapper.RefundInfoMapper;
import com.youhaowu.order.dto.RefundInfoQueryDTO;
import com.youhaowu.order.service.RefundInfoService;
import com.youhaowu.order.vo.RefundInfoVO;
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
public class RefundInfoServiceImpl implements RefundInfoService {

    @Autowired
    private RefundInfoMapper refundInfoMapper;

    @Override
    public PageData<RefundInfoVO> page(RefundInfoQueryDTO query) {
        LambdaQueryWrapper<RefundInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(refundInfoMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public RefundInfoVO getById(Long id) {
        RefundInfoEntity e = refundInfoMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(RefundInfoVO vo) {
        RefundInfoEntity e = new RefundInfoEntity();
        BeanUtil.copyProperties(vo, e);
        return refundInfoMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<RefundInfoVO> list) {
        List<RefundInfoEntity> entities = list.stream().map(vo -> {
            RefundInfoEntity e = new RefundInfoEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return refundInfoMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, RefundInfoVO vo) {
        RefundInfoEntity e = new RefundInfoEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return refundInfoMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return refundInfoMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return refundInfoMapper.deleteBatchIds(ids);
    }

    private RefundInfoVO entityToVO(RefundInfoEntity e) {
        RefundInfoVO v = new RefundInfoVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
