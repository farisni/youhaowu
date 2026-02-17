package com.wheatmall.authadmin.mock;

import com.wheatmall.authadmin.entity.SysPermission;
import com.wheatmall.authadmin.entity.SysRole;
import com.wheatmall.authadmin.entity.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * RBAC 假数据提供类（演示用，不连接数据库）
 * 
 * 模拟数据包括：
 * - 3个用户：admin(超管)、user(普通用户)、test(测试用户)
 * - 2个角色：SUPER_ADMIN、USER
 * - 4个权限：user:view、user:create、user:update、user:delete
 */
public class MockData {
    
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // ========== 用户数据 ==========
    private static final Map<Long, SysUser> USER_MAP = new HashMap<>();
    private static final Map<String, SysUser> USER_NAME_MAP = new HashMap<>();
    
    // ========== 角色数据 ==========
    private static final Map<Long, SysRole> ROLE_MAP = new HashMap<>();
    private static final Map<String, SysRole> ROLE_CODE_MAP = new HashMap<>();
    
    // ========== 权限数据 ==========
    private static final Map<Long, SysPermission> PERMISSION_MAP = new HashMap<>();
    private static final Map<String, SysPermission> PERMISSION_CODE_MAP = new HashMap<>();
    
    // ========== 关联关系 ==========
    // 用户ID -> 角色ID列表
    private static final Map<Long, List<Long>> USER_ROLE_MAP = new HashMap<>();
    // 角色ID -> 权限ID列表
    private static final Map<Long, List<Long>> ROLE_PERMISSION_MAP = new HashMap<>();
    
    // 模拟Token黑名单（替代Redis）
    private static final Set<String> TOKEN_BLACKLIST = new HashSet<>();
    // 模拟RefreshToken存储（替代Redis）
    private static final Map<Long, String> REFRESH_TOKEN_MAP = new HashMap<>();
    
    static {
        initUsers();
        initRoles();
        initPermissions();
        initUserRoleRelations();
        initRolePermissionRelations();
    }
    
    /**
     * 初始化用户数据
     */
    private static void initUsers() {
        // 超级管理员
        SysUser admin = SysUser.of(
            1L, 
            "admin", 
            passwordEncoder.encode("123456"),
            "admin@wheatmall.com"
        );
        admin.setPhone("13800138000");
        
        // 普通用户
        SysUser user = SysUser.of(
            2L, 
            "user", 
            passwordEncoder.encode("123456"),
            "user@wheatmall.com"
        );
        user.setPhone("13800138001");
        
        // 测试用户
        SysUser test = SysUser.of(
            3L, 
            "test", 
            passwordEncoder.encode("123456"),
            "test@wheatmall.com"
        );
        test.setPhone("13800138002");
        test.setStatus(0); // 禁用状态
        
        // 存入Map
        USER_MAP.put(admin.getId(), admin);
        USER_MAP.put(user.getId(), user);
        USER_MAP.put(test.getId(), test);
        
        USER_NAME_MAP.put(admin.getUsername(), admin);
        USER_NAME_MAP.put(user.getUsername(), user);
        USER_NAME_MAP.put(test.getUsername(), test);
    }
    
    /**
     * 初始化角色数据
     */
    private static void initRoles() {
        SysRole superAdmin = SysRole.of(
            1L, 
            "超级管理员", 
            "SUPER_ADMIN", 
            "系统超级管理员，拥有所有权限"
        );
        
        SysRole userRole = SysRole.of(
            2L, 
            "普通用户", 
            "USER", 
            "普通注册用户，拥有基本权限"
        );
        
        SysRole testRole = SysRole.of(
            3L, 
            "测试人员", 
            "TESTER", 
            "测试人员角色"
        );
        
        ROLE_MAP.put(superAdmin.getId(), superAdmin);
        ROLE_MAP.put(userRole.getId(), userRole);
        ROLE_MAP.put(testRole.getId(), testRole);
        
        ROLE_CODE_MAP.put(superAdmin.getRoleCode(), superAdmin);
        ROLE_CODE_MAP.put(userRole.getRoleCode(), userRole);
        ROLE_CODE_MAP.put(testRole.getRoleCode(), testRole);
    }
    
    /**
     * 初始化权限数据
     */
    private static void initPermissions() {
        SysPermission view = SysPermission.of(1L, "用户查看", "user:view", "api");
        SysPermission create = SysPermission.of(2L, "用户创建", "user:create", "api");
        SysPermission update = SysPermission.of(3L, "用户编辑", "user:update", "api");
        SysPermission delete = SysPermission.of(4L, "用户删除", "user:delete", "api");
        SysPermission admin = SysPermission.of(5L, "管理员权限", "user:admin", "api");
        
        PERMISSION_MAP.put(view.getId(), view);
        PERMISSION_MAP.put(create.getId(), create);
        PERMISSION_MAP.put(update.getId(), update);
        PERMISSION_MAP.put(delete.getId(), delete);
        PERMISSION_MAP.put(admin.getId(), admin);
        
        PERMISSION_CODE_MAP.put(view.getPermCode(), view);
        PERMISSION_CODE_MAP.put(create.getPermCode(), create);
        PERMISSION_CODE_MAP.put(update.getPermCode(), update);
        PERMISSION_CODE_MAP.put(delete.getPermCode(), delete);
        PERMISSION_CODE_MAP.put(admin.getPermCode(), admin);
    }
    
    /**
     * 初始化用户-角色关联
     */
    private static void initUserRoleRelations() {
        // admin -> SUPER_ADMIN
        USER_ROLE_MAP.put(1L, Arrays.asList(1L));
        
        // user -> USER
        USER_ROLE_MAP.put(2L, Arrays.asList(2L));
        
        // test -> TESTER
        USER_ROLE_MAP.put(3L, Arrays.asList(3L));
    }
    
    /**
     * 初始化角色-权限关联
     */
    private static void initRolePermissionRelations() {
        // SUPER_ADMIN -> 所有权限
        ROLE_PERMISSION_MAP.put(1L, Arrays.asList(1L, 2L, 3L, 4L, 5L));
        
        // USER -> 查看权限
        ROLE_PERMISSION_MAP.put(2L, Arrays.asList(1L));
        
        // TESTER -> 查看、创建
        ROLE_PERMISSION_MAP.put(3L, Arrays.asList(1L, 2L));
    }
    
    // ========== 查询方法 ==========
    
    /**
     * 根据用户名查询用户
     */
    public static SysUser getUserByUsername(String username) {
        return USER_NAME_MAP.get(username);
    }
    
    /**
     * 根据用户ID查询用户
     */
    public static SysUser getUserById(Long userId) {
        return USER_MAP.get(userId);
    }
    
    /**
     * 查询用户角色列表
     */
    public static List<SysRole> getRolesByUserId(Long userId) {
        List<Long> roleIds = USER_ROLE_MAP.getOrDefault(userId, Collections.emptyList());
        List<SysRole> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysRole role = ROLE_MAP.get(roleId);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }
    
    /**
     * 查询用户权限列表
     */
    public static List<SysPermission> getPermissionsByUserId(Long userId) {
        List<Long> roleIds = USER_ROLE_MAP.getOrDefault(userId, Collections.emptyList());
        Set<Long> permissionIds = new HashSet<>();
        
        // 收集所有角色的权限
        for (Long roleId : roleIds) {
            List<Long> perms = ROLE_PERMISSION_MAP.getOrDefault(roleId, Collections.emptyList());
            permissionIds.addAll(perms);
        }
        
        List<SysPermission> permissions = new ArrayList<>();
        for (Long permId : permissionIds) {
            SysPermission perm = PERMISSION_MAP.get(permId);
            if (perm != null) {
                permissions.add(perm);
            }
        }
        return permissions;
    }
    
    /**
     * 查询用户角色编码列表
     */
    public static List<String> getRoleCodesByUserId(Long userId) {
        List<SysRole> roles = getRolesByUserId(userId);
        List<String> roleCodes = new ArrayList<>();
        for (SysRole role : roles) {
            roleCodes.add(role.getRoleCode());
        }
        return roleCodes;
    }
    
    /**
     * 查询用户权限编码列表
     */
    public static List<String> getPermissionCodesByUserId(Long userId) {
        List<SysPermission> permissions = getPermissionsByUserId(userId);
        List<String> permCodes = new ArrayList<>();
        for (SysPermission perm : permissions) {
            permCodes.add(perm.getPermCode());
        }
        return permCodes;
    }
    
    // ========== Token黑名单模拟（替代Redis）==========
    
    /**
     * 将Token加入黑名单
     */
    public static void addToBlacklist(String token) {
        TOKEN_BLACKLIST.add(token);
    }
    
    /**
     * 检查Token是否在黑名单
     */
    public static boolean isTokenBlacklisted(String token) {
        return TOKEN_BLACKLIST.contains(token);
    }
    
    // ========== RefreshToken模拟（替代Redis）==========
    
    /**
     * 存储RefreshToken
     */
    public static void storeRefreshToken(Long userId, String refreshToken) {
        REFRESH_TOKEN_MAP.put(userId, refreshToken);
    }
    
    /**
     * 获取RefreshToken
     */
    public static String getRefreshToken(Long userId) {
        return REFRESH_TOKEN_MAP.get(userId);
    }
    
    /**
     * 删除RefreshToken
     */
    public static void removeRefreshToken(Long userId) {
        REFRESH_TOKEN_MAP.remove(userId);
    }
    
    /**
     * 获取所有用户（调试用）
     */
    public static List<SysUser> getAllUsers() {
        return new ArrayList<>(USER_MAP.values());
    }
    
    /**
     * 验证密码
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    /**
     * 加密密码
     */
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
