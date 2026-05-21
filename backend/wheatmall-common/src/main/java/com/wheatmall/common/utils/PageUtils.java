package com.wheatmall.common.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wheatmall.common.dto.BaseQueryDTO;

/**
 * MyBatis-Plus 分页工具
 */
public class PageUtils {

    /** 只允许字母、数字、下划线的列名，防止 SQL 注入 */
    private static final String COLUMN_PATTERN = "^[a-zA-Z_]\\w*$";

    /**
     * 从 BaseQueryDTO 构建 MP Page 对象
     */
    public static <T> Page<T> buildPage(BaseQueryDTO query) {
        long pageNum = query.getPageNum() != null ? query.getPageNum() : 1L;
        long pageSize = query.getPageSize() != null ? query.getPageSize() : 10L;
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 应用动态排序：sortField 有值时动态排序，否则使用默认排序列
     */
    public static <T> void applySort(LambdaQueryWrapper<T> wrapper,
                                      BaseQueryDTO query,
                                      SFunction<T, ?> defaultSortColumn) {
        if (StrUtil.isNotBlank(query.getSortField())) {
            String field = query.getSortField().trim();
            if (!field.matches(COLUMN_PATTERN)) {
                throw new IllegalArgumentException("非法的排序字段: " + field);
            }
            String direction = !Boolean.FALSE.equals(query.getSortAsc()) ? " ASC" : " DESC";
            wrapper.last("ORDER BY " + field + direction);
        } else {
            boolean asc = query.getSortAsc() != null ? query.getSortAsc() : true;
            wrapper.orderBy(true, asc, defaultSortColumn);
        }
    }
}
