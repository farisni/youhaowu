package com.wheatmall.authadmin.security.service;

import com.wheatmall.authadmin.entity.SysUser;
import com.wheatmall.authadmin.mock.MockData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户详情服务实现（使用Mock数据）
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        SysUser user = MockData.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 2. 查询用户角色
        List<String> roleCodes = MockData.getRoleCodesByUserId(user.getId());
        
        // 3. 查询用户权限
        List<String> permissionCodes = MockData.getPermissionCodesByUserId(user.getId());
        
        // 4. 构建 UserDetails 对象
        return new SecurityUser(user, roleCodes, permissionCodes);
    }
}
