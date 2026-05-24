package com.youhaowu.common.vo.seckill;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 秒杀场次信息（含关联 SKU 列表）
 */
@Data
public class SeckillSessionWithSkusTO {

    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Date createTime;
    private List<SeckillSkuVO> relationSkus;
}
