package com.youhaowu.thirdparty.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youhaowu.common.utils.R;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;

/**
 * MinIO 文件上传签名接口，生成 presigned PUT URL 供前端直传
 */
@Slf4j
@RestController
@RequestMapping("/api/thirdparty/oss")
public class OSSController {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.bucket}")
    private String bucket;

    @GetMapping("/policy")
    public R policy() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/";
        String objectName = dir + UUID.randomUUID().toString().replace("-", "");

        Map<String, String> respMap = new LinkedHashMap<>();
        try {
            int expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;

            //  生成 MinIO presigned PUT URL
            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(bucket)
                    .object(objectName)
                    .expiry(expireTime, TimeUnit.SECONDS)
                    .build()
            );

            respMap.put("url", url);
            respMap.put("dir", dir);
            respMap.put("host", endpoint);
            String accessUrl = endpoint + "/" + bucket + "/" + objectName;
            respMap.put("accessUrl", accessUrl);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            log.error("生成 MinIO presigned URL 失败", e);
            return R.fail("生成上传签名失败");
        }
        return R.ok(respMap);
    }
}
