package com.youhaowu.common.vo.seckill;

import java.time.Instant;
import java.util.List;

/**
 * 秒杀场次信息（含关联 SKU 列表）
 */
public record SeckillSessionWithSkusTO(
    Long id,
    String name,
    Instant startTime,
    Instant endTime,
    Integer status,
    Instant createTime,
    List<SeckillSkuVO> relationSkus
) {}
