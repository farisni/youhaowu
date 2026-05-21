package com.wheatmall.product.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@Component
public class ApiLogInterceptor implements HandlerInterceptor {

    private static final String TOPIC = "wheatmall.api.log";
    private static final String START_TIME_ATTR = "apiLogStartTime";

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
        Long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;

        JSONObject entry = new JSONObject();
        entry.put("timestamp", Instant.now().toString());
        entry.put("service", "wheatmall-product");
        entry.put("method", request.getMethod());
        entry.put("path", request.getRequestURI());
        entry.put("query", request.getQueryString());
        entry.put("body", getRequestBody(request));
        entry.put("status", response.getStatus());
        entry.put("duration", duration);
        entry.put("ip", getClientIp(request));

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
