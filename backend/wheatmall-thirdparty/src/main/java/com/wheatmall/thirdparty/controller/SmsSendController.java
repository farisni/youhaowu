package com.wheatmall.thirdparty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.wheatmall.thirdparty.component.SmsComponent;
import com.wheatmall.common.utils.R;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("thirdparty/sms")
public class SmsSendController {


    @Autowired
    SmsComponent smsComponent;
    @GetMapping("/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        log.info("调用aliyun短信服务，Code: {}", code);
        SendSmsResponse sendSmsResponse = smsComponent.sendSmsCode(phone, code);
        if ("OK".equals(sendSmsResponse.getBody().getCode())) {
            return R.ok();
        } else {
            return R.fail(sendSmsResponse.getBody().getMessage());
        }
    }
}
