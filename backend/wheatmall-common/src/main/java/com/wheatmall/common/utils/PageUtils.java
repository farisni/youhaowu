package com.wheatmall.common.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wheatmall.common.dto.BaseQueryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * 应用动态排序：仅当 sortField 有值时生效
     */
    public static <T> void applySort(LambdaQueryWrapper<T> wrapper, BaseQueryDTO query) {
        if (StrUtil.isNotBlank(query.getSortField())) {
            String field = query.getSortField().trim();
            if (!field.matches(COLUMN_PATTERN)) {
                throw new IllegalArgumentException("非法的排序字段: " + field);
            }
            String direction = !Boolean.FALSE.equals(query.getSortAsc()) ? " ASC" : " DESC";
            wrapper.last("ORDER BY " + field + direction);
        }
    }

    /**
     * 应用时间范围条件
     */
    public static <T> void applyTimeRange(LambdaQueryWrapper<T> wrapper,
                                           BaseQueryDTO query,
                                           SFunction<T, LocalDateTime> column) {
        if (query.getCreateTimeBegin() != null) {
            wrapper.ge(column, query.getCreateTimeBegin());
        }
        if (query.getCreateTimeEnd() != null) {
            wrapper.le(column, query.getCreateTimeEnd());
        }
    }

    /**
     * 执行分页查询并转换为 VO 列表
     */
    public static <E, V> PageData<V> selectPage(BaseMapper<E> mapper,
                                                  LambdaQueryWrapper<E> wrapper,
                                                  BaseQueryDTO query,
                                                  Function<E, V> converter) {
        applySort(wrapper, query);
        Page<E> result = mapper.selectPage(buildPage(query), wrapper);
        List<V> voList = result.getRecords().stream().map(converter).collect(Collectors.toList());
        return PageData.of(result.getTotal(), result.getSize(), result.getCurrent(), voList);
    }
}
