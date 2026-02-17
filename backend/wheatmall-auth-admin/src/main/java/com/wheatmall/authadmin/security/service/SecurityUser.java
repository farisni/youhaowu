package com.wheatmall.authadmin.security.service;

import com.wheatmall.authadmin.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security 用户封装类
 */
public class SecurityUser implements UserDetails {
    
    private SysUser user;
    private List<String> roles;
    private List<String> permissions;
    
    public SecurityUser(SysUser user, List<String> roles, List<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 合并角色和权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // 角色以 ROLE_ 前缀
        roles.forEach(role -> authorities.add(
            new SimpleGrantedAuthority("ROLE_" + role)));
        
        // 权限直接添加
        permissions.forEach(perm -> authorities.add(
            new SimpleGrantedAuthority(perm)));
            
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return user.getStatus() == 1;
    }
    
    public SysUser getUser() {
        return user;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public List<String> getPermissions() {
        return permissions;
    }
}
