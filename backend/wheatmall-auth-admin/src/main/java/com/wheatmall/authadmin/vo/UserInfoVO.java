package com.wheatmall.authadmin.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 用户信息 VO
 */
@Data
@Builder
public class UserInfoVO {
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;
    private List<String> permissions;
}
