package com.youhaowu.order.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.order.dto.MqMessageQueryDTO;
import com.youhaowu.order.vo.MqMessageVO;

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
