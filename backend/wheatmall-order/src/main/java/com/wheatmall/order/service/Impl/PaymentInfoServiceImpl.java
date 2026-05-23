package com.wheatmall.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.entity.PaymentInfoEntity;
import com.wheatmall.order.mapper.PaymentInfoMapper;
import com.wheatmall.order.dto.PaymentInfoQueryDTO;
import com.wheatmall.order.service.PaymentInfoService;
import com.wheatmall.order.vo.PaymentInfoVO;
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
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Override
    public PageData<PaymentInfoVO> page(PaymentInfoQueryDTO query) {
        LambdaQueryWrapper<PaymentInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            //  关键字搜索按需扩展
        }
        return PageUtils.selectPage(paymentInfoMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public PaymentInfoVO getById(Long id) {
        PaymentInfoEntity e = paymentInfoMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(PaymentInfoVO vo) {
        PaymentInfoEntity e = new PaymentInfoEntity();
        BeanUtil.copyProperties(vo, e);
        return paymentInfoMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<PaymentInfoVO> list) {
        List<PaymentInfoEntity> entities = list.stream().map(vo -> {
            PaymentInfoEntity e = new PaymentInfoEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return paymentInfoMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, PaymentInfoVO vo) {
        PaymentInfoEntity e = new PaymentInfoEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return paymentInfoMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return paymentInfoMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return paymentInfoMapper.deleteBatchIds(ids);
    }

    private PaymentInfoVO entityToVO(PaymentInfoEntity e) {
        PaymentInfoVO v = new PaymentInfoVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
