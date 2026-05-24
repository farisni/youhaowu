package com.youhaowu.thirdparty.component;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;

import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SmsComponent {

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessKeyId;
    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessKeySecret;
    @Value("${spring.cloud.alicloud.sms.region}")
    private String region;

    @Value("${spring.cloud.alicloud.sms.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.sms.signName}")
    private String signName;

    @Value("${spring.cloud.alicloud.sms.templateCode}")
    private String templateCode;

    public SendSmsResponse sendSmsCode(String phone, String code) {

        SendSmsResponse rslt = null;
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKeyId)
                .accessKeySecret(accessKeySecret)
                //.securityToken("<your-token>") // use STS token
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region(region) // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(endpoint)
                        //.setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName(signName)
                .templateCode(templateCode)
                .phoneNumbers(phone)
                .templateParam("{\"code\":\""+code+"\"}")
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        try {
            SendSmsResponse resp = response.get();
            rslt = resp;
            if ("OK".equals(resp.getBody().getCode())) {
                log.info("sms短信发送成功！返回结果: {}", new Gson().toJson(resp));
            } else {
                log.error("sms短信发送失败！返回: {}", new Gson().toJson(resp));
            }
        }catch (Exception ex) {
            log.error("sms服务返回项目失败！", ex);
        }
        client.close();
        return rslt;
    }
}
