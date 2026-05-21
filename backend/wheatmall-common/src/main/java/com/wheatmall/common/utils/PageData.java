package com.wheatmall.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据封装
 */
@Data
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;
    /** 每页条数 */
    private long pageSize;
    /** 当前页码 */
    private long pageNum;
    /** 数据列表 */
    private List<T> list;

    public static <T> PageData<T> of(long total, long pageSize, long pageNum, List<T> list) {
        PageData<T> page = new PageData<>();
        page.setTotal(total);
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        page.setList(list);
        return page;
    }
}
