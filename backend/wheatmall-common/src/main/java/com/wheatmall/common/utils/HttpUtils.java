package com.wheatmall.common.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpUtils {

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * HTTP GET 请求（简化版，兼容旧 Gitee OAuth 调用）
     */
    public static ResponseEntity<String> doGet(String host, String path, String method,
                                                Map<String, String> headers,
                                                Map<String, String> queries) {
        StringBuilder url = new StringBuilder(host + path);
        if (queries != null && !queries.isEmpty()) {
            url.append("?");
            queries.forEach((k, v) -> url.append(k).append("=").append(v).append("&"));
            url.setLength(url.length() - 1);
        }
        return restTemplate.exchange(url.toString(), HttpMethod.GET, null, String.class);
    }
}
