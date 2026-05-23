package com.wheatmall.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * SpuComment VO
 */
@Data
public class SpuCommentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long skuId;
    private Long spuId;
    private String spuName;
    private String memberNickName;
    private Integer star;
    private String memberIp;
    private Date createTime;
    private Integer showStatus;
    private String spuAttributes;
    private Integer likesCount;
    private Integer replyCount;
    private String resources;
    private String content;
    private String memberIcon;
    private Integer commentType;
}
