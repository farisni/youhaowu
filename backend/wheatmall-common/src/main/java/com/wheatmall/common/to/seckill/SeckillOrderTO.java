package com.wheatmall.common.to.seckill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀订单消息 DTO（发送到 Kafka，由订单模块消费）
 */
@Data
public class SeckillOrderTO {

    private String orderSn;
    private Long promotionSessionId;
    private Long skuId;
    private BigDecimal seckillPrice;
    private Integer num;
    private Long memberId;
}
