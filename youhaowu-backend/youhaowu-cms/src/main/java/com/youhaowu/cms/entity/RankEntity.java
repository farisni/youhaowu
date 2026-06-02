package com.youhaowu.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("cms_rank")
public class RankEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String imgUrl;
    private String price;
    private String tabType;
    private Integer sort;
    @TableLogic(value = "1", delval = "0")
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
