package com.youhaowu.member.vo;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MemberUserRegisterVO {

    private String userName;


    private String password;


    private String phone;

    private String code;
}
