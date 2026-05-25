package com.youhaowu.common.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用分页查询参数基类
 * 所有分页查询 DTO 继承此类
 */
@Data
public class BaseQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 当前页码 */
    private Long pageNum = 1L;
    /** 每页条数 */
    private Long pageSize = 10L;
    /** 关键字搜索 */
    private String keyword;
    /** 排序字段（对应 Entity 属性名，如 "sort"、"createTime"） */
    private String sortField;
    /** 是否升序 */
    private Boolean sortAsc = true;
    /** 创建时间起始 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeBegin;
    /** 创建时间截止 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;
}
