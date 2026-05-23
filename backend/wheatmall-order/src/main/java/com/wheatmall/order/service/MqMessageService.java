package com.wheatmall.order.service;

import com.wheatmall.common.utils.PageData;
import com.wheatmall.order.query.MqMessageQueryDTO;
import com.wheatmall.order.vo.MqMessageVO;

import java.util.List;

/**
 * MqMessage Service
 */
public interface MqMessageService {

    PageData<MqMessageVO> page(MqMessageQueryDTO query);
    MqMessageVO getById(Long id);
    Integer save(MqMessageVO vo);
    Integer saveBatch(List<MqMessageVO> list);
}
