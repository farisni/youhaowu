package com.youhaowu.common.constant;

/**
 * API日志相关常量
 */
public final class ApiLogConstants {

    private ApiLogConstants() {
    }

    /** Kafka Topic */
    public static final String TOPIC = "wheatmall.api.log";

    /** 请求开始时间属性名 */
    public static final String START_TIME_ATTR = "apiLogStartTime";

    /** 服务名称 */
    public static final String SERVICE_NAME = "youhaowu-product";
}
