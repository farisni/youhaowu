package com.youhaowu.thirdparty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.youhaowu.thirdparty.component.SmsComponent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/thirdparty/sms")
public class SmsSendController {

    @Autowired
    SmsComponent smsComponent;

    @GetMapping("/sendCode")
    public Boolean sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        log.info("调用aliyun短信服务，Code: {}", code);
        SendSmsResponse sendSmsResponse = smsComponent.sendSmsCode(phone, code);
        return "OK".equals(sendSmsResponse.getBody().getCode());
    }
}
