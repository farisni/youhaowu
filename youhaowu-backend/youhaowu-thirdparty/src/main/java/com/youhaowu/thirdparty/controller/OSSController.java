package com.youhaowu.thirdparty.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youhaowu.common.config.MinioProperties;

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

    @Autowired
    private MinioProperties minioProperties;

    @GetMapping("/policy")
    public Map<String, String> policy(@RequestParam String fileName) {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/";

        int dotIdx = fileName.lastIndexOf('.');
        String baseName, ext;
        if (dotIdx > 0) {
            baseName = fileName.substring(0, dotIdx);
            ext = fileName.substring(dotIdx);
        } else {
            baseName = fileName;
            ext = "";
        }
        String objectName = dir + baseName + "_" + (System.currentTimeMillis() / 1000) + ext;

        Map<String, String> respMap = new LinkedHashMap<>();
        try {
            int expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;

            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(minioProperties.getBucket())
                    .object(objectName)
                    .expiry(expireTime, TimeUnit.SECONDS)
                    .build()
            );

            respMap.put("url", url);
            respMap.put("dir", dir);
            respMap.put("host", minioProperties.getEndpoint());
            //  数据库存相对路径，展示时拼完整 URL
            respMap.put("accessUrl", "/" + minioProperties.getBucket() + "/" + objectName);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            log.error("生成 MinIO presigned URL 失败", e);
            throw new RuntimeException("生成上传签名失败");
        }
        return respMap;
    }
}
