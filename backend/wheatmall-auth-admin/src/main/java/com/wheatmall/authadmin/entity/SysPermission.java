package com.wheatmall.authadmin.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 系统权限实体类（演示用假数据）
 */
@Data
public class SysPermission {
    
    /**
     * 权限ID
     */
    private Long id;
    
    /**
     * 权限名称
     */
    private String permName;
    
    /**
     * 权限编码（如：user:create）
     */
    private String permCode;
    
    /**
     * 类型: menu-菜单, button-按钮, api-接口
     */
    private String type;
    
    /**
     * 父权限ID
     */
    private Long parentId;
    
    /**
     * 排序
     */
    private Integer sortOrder;
    
    /**
     * 状态: 0-禁用, 1-启用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 快速构建方法
     */
    public static SysPermission of(Long id, String permName, String permCode, String type) {
        SysPermission permission = new SysPermission();
        permission.setId(id);
        permission.setPermName(permName);
        permission.setPermCode(permCode);
        permission.setType(type);
        permission.setParentId(0L);
        permission.setSortOrder(0);
        permission.setStatus(1);
        permission.setCreateTime(LocalDateTime.now());
        return permission;
    }
}
