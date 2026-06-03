package com.youhaowu.common.vo.auth;


public record UserRegisterVO(
    String userName,
    String password,
    String phone
) {}
