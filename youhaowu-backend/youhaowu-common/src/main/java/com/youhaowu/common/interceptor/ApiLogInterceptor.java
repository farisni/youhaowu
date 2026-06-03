package com.youhaowu.common.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.youhaowu.common.constant.ApiLogConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * API 日志拦截器，将请求信息异步写入 Kafka。
 * <p>不带 @Component，各模块需在 Config 中 @Bean 手动注册。</p>
 */
@Slf4j
public class ApiLogInterceptor implements HandlerInterceptor {

    private static final String TOPIC = ApiLogConstants.TOPIC;
    private static final String START_TIME_ATTR = ApiLogConstants.START_TIME_ATTR;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        //  计算耗时
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;

        //  组装日志条目
        JSONObject entry = new JSONObject();
        entry.put("timestamp", Instant.now().toString());
        entry.put("service", ApiLogConstants.SERVICE_NAME);
        entry.put("method", request.getMethod());
        entry.put("path", request.getRequestURI());
        entry.put("query", request.getQueryString());
        entry.put("body", getRequestBody(request));
        entry.put("status", response.getStatus());
        entry.put("duration", duration);
        entry.put("ip", getClientIp(request));

        //  异步推送 Kafka
        kafkaTemplate.send(TOPIC, request.getRequestURI(), entry.toJSONString())
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.warn("Kafka 发送失败: {}", throwable.getMessage());
                    }
                });
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            if (content.length > 0) {
                return new String(content, StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
