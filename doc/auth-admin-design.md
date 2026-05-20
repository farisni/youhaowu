# JWT 认证系统开发规划文档

> 文档版本: v1.0  
> 创建日期: 2026-02-17  
> 模块: wheatmall-auth-admin  
> 作者: AI Assistant

---

## 一、总体架构

```
┌─────────────────────────────────────────────────────────────────┐
│                        客户端层 (Client)                        │
│              浏览器 / App / 第三方服务                           │
│                   Authorization: Bearer {token}                 │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                      过滤器链 (Filter Chain)                     │
│            JwtAuthenticationFilter                              │
│            ├── 拦截所有请求                                      │
│            ├── 提取 Bearer Token                                │
│            ├── 验证 Token 有效性                                │
│            ├── 查询 Redis 黑名单                                │
│            └── 设置 SecurityContext                             │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                   Spring Security 核心层                        │
│            ├── UserDetailsService                               │
│            │   └── 从数据库加载用户+权限                         │
│            ├── PasswordEncoder                                  │
│            │   └── BCryptPasswordEncoder                        │
│            └── SecurityFilterChain                              │
│                └── 配置安全规则                                  │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                   JWT + Redis 层                                │
│            ┌─────────────────┐    ┌─────────────────────┐      │
│            │   JwtUtil       │    │   RedisTemplate     │      │
│            │   ├── 签发Token │    │   ├── Token黑名单    │      │
│            │   ├── 解析Token │    │   └── Token缓存      │      │
│            │   └── 验证Token │    │                     │      │
│            └─────────────────┘    └─────────────────────┘      │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│                   业务层 + 数据层                                │
│            ┌─────────────────┐    ┌─────────────────────┐      │
│            │  AuthService    │    │   RBAC 三表          │      │
│            │  ├── 登录       │    │   ├── sys_user      │      │
│            │  ├── 登出       │    │   ├── sys_role      │      │
│            │  └── 刷新Token  │    │   ├── sys_permission│      │
│            └─────────────────┘    │   └── 关联表        │      │
│                                   └─────────────────────┘      │
└─────────────────────────────────────────────────────────────────┘
```

---

## 二、执行步骤

### Phase 1: 基础环境准备 (预计 30 分钟)

#### Step 1: 完善当前模块配置
- **操作**: 创建 application.yml 配置文件
- **文件**: `src/main/resources/application.yml`
- **内容要点**:
  - 服务端口: 8092
  - 服务名称: wheatmall-auth-admin
  - Nacos 注册中心配置
  - Redis 连接配置
  - JWT 密钥和过期时间配置

#### Step 2: 数据库设计 - RBAC 三表
- **操作**: 创建数据库表结构 SQL 文件
- **文件**: `doc/sql/rbac_schema.sql`
- **表结构**:

```sql
-- 1. 用户表 sys_user
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(64) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '加密密码',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) COMMENT '系统用户表';

-- 2. 角色表 sys_role
CREATE TABLE sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(64) NOT NULL UNIQUE COMMENT '角色编码(如: ADMIN, USER)',
    description VARCHAR(255) COMMENT '角色描述',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '系统角色表';

-- 3. 权限表 sys_permission
CREATE TABLE sys_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    perm_name VARCHAR(64) NOT NULL COMMENT '权限名称',
    perm_code VARCHAR(128) NOT NULL UNIQUE COMMENT '权限编码(如: user:create)',
    type VARCHAR(20) COMMENT '类型: menu-菜单, button-按钮, api-接口',
    parent_id BIGINT DEFAULT 0 COMMENT '父权限ID',
    sort_order INT DEFAULT 0,
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '系统权限表';

-- 4. 用户-角色关联表
CREATE TABLE sys_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_role (user_id, role_id)
) COMMENT '用户角色关联表';

-- 5. 角色-权限关联表
CREATE TABLE sys_role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_role_permission (role_id, permission_id)
) COMMENT '角色权限关联表';

-- 初始化数据
INSERT INTO sys_role (role_name, role_code, description) VALUES 
('超级管理员', 'SUPER_ADMIN', '系统超级管理员'),
('普通用户', 'USER', '普通注册用户');

INSERT INTO sys_permission (perm_name, perm_code, type) VALUES 
('用户查看', 'user:view', 'api'),
('用户创建', 'user:create', 'api'),
('用户编辑', 'user:update', 'api'),
('用户删除', 'user:delete', 'api');
```

#### Step 3: 更新父工程 pom.xml
- **操作**: 在父工程中声明新模块
- **文件**: `backend/pom.xml`
- **修改内容**: 在 `<modules>` 中添加 `<module>wheatmall-auth-admin</module>`

---

### Phase 2: 核心组件开发 (预计 60 分钟)

#### Step 4: 添加 JWT 依赖
- **操作**: 在 auth-admin 模块添加 JWT 相关依赖
- **文件**: `wheatmall-auth-admin/pom.xml`
- **依赖**:

```xml
<!-- JJWT - Java JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- Redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

#### Step 5: 创建 JWT 工具类 (JwtUtil)
- **操作**: 实现 Token 生成、解析、验证
- **文件**: `src/main/java/com/wheatmall/authadmin/security/jwt/JwtUtil.java`
- **核心方法**:

```java
@Component
public class JwtUtil {
    // 生成 Access Token (30分钟)
    public String generateAccessToken(Long userId, String username, List<String> roles)
    
    // 生成 Refresh Token (7天)
    public String generateRefreshToken(Long userId)
    
    // 解析 Token 获取 Claims
    public Claims parseToken(String token)
    
    // 验证 Token 是否有效
    public boolean validateToken(String token)
    
    // 从 Token 获取用户ID
    public Long getUserIdFromToken(String token)
    
    // 从 Token 获取用户名
    public String getUsernameFromToken(String token)
    
    // 获取 Token 过期时间
    public Date getExpirationDate(String token)
}
```

#### Step 6: 创建 JWT 过滤器 (JwtAuthenticationFilter)
- **操作**: 拦截请求、解析验证 JWT
- **文件**: `src/main/java/com/wheatmall/authadmin/security/filter/JwtAuthenticationFilter.java`
- **逻辑**:

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) {
        // 1. 从请求头获取 Authorization: Bearer <token>
        String authHeader = request.getHeader("Authorization");
        
        // 2. 检查是否为 Bearer Token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            // 3. 查询 Redis 黑名单（是否已登出）
            if (isTokenBlacklisted(token)) {
                throw new TokenInvalidException("Token已失效");
            }
            
            // 4. 验证 Token 有效性
            if (jwtUtil.validateToken(token)) {
                // 5. 解析 Token 获取用户信息
                Long userId = jwtUtil.getUserIdFromToken(token);
                String username = jwtUtil.getUsernameFromToken(token);
                
                // 6. 加载用户权限
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // 7. 创建 Authentication 对象
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                // 8. 设置 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

#### Step 7: 创建 UserDetailsService 实现
- **操作**: 从数据库加载用户和权限
- **文件**: `src/main/java/com/wheatmall/authadmin/security/service/UserDetailsServiceImpl.java`
- **逻辑**:

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 1. 查询用户
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 2. 查询用户角色
        List<String> roles = userMapper.selectRolesByUserId(user.getId());
        
        // 3. 查询用户权限
        List<String> permissions = userMapper.selectPermissionsByUserId(user.getId());
        
        // 4. 构建 UserDetails 对象
        return new SecurityUser(user, roles, permissions);
    }
}

// 自定义 UserDetails 实现
public class SecurityUser implements UserDetails {
    private SysUser user;
    private List<String> roles;
    private List<String> permissions;
    
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
    
    // ... 其他方法实现
}
```

#### Step 8: 配置 Spring Security
- **操作**: 配置安全规则和过滤器链
- **文件**: `src/main/java/com/wheatmall/authadmin/config/SecurityConfig.java`
- **配置**:

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // 启用方法级权限注解
public class SecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用 CSRF（使用 JWT 不需要）
            .csrf(csrf -> csrf.disable())
            
            // 配置 Session 管理（无状态）
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 配置请求授权
            .authorizeHttpRequests(auth -> auth
                // 公开端点
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // 其他需要认证
                .anyRequest().authenticated()
            )
            
            // 添加 JWT 过滤器
            .addFilterBefore(jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

---

### Phase 3: 业务层开发 (预计 45 分钟)

#### Step 9: 创建 DTO 对象
- **操作**: 创建请求和响应的 DTO
- **文件**:
  - `src/main/java/com/wheatmall/authadmin/dto/LoginRequest.java`
  - `src/main/java/com/wheatmall/authadmin/dto/LoginResponse.java`
  - `src/main/java/com/wheatmall/authadmin/dto/RefreshTokenRequest.java`

```java
@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;
}

@Data
@Builder
public class LoginResponse {
    private String accessToken;      // 访问令牌
    private String refreshToken;     // 刷新令牌
    private Long expiresIn;          // 过期时间(秒)
    private String tokenType;        // Bearer
    private UserInfoVO userInfo;     // 用户信息
}
```

#### Step 10: 创建 VO 对象
- **操作**: 创建返回前端的视图对象
- **文件**: `src/main/java/com/wheatmall/authadmin/vo/UserInfoVO.java`

```java
@Data
@Builder
public class UserInfoVO {
    private Long userId;
    private String username;
    private String email;
    private List<String> roles;
    private List<String> permissions;
}
```

#### Step 11: 创建 AuthService
- **操作**: 实现登录、登出、刷新业务逻辑
- **文件**: `src/main/java/com/wheatmall/authadmin/service/AuthService.java`
- **接口**:

```java
public interface AuthService {
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 用户登出
     */
    void logout(String token);
    
    /**
     * 刷新 Token
     */
    LoginResponse refreshToken(String refreshToken);
    
    /**
     * 获取当前登录用户信息
     */
    UserInfoVO getCurrentUser();
}
```

**实现类** `AuthServiceImpl.java`:

```java
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. 认证用户
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword())
        );
        
        // 2. 获取认证后的用户信息
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        SysUser user = securityUser.getUser();
        
        // 3. 生成 Token
        String accessToken = jwtUtil.generateAccessToken(
            user.getId(), 
            user.getUsername(), 
            securityUser.getRoles()
        );
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());
        
        // 4. 存储 Refresh Token 到 Redis
        redisTemplate.opsForValue().set(
            "refresh_token:" + user.getId(),
            refreshToken,
            7,
            TimeUnit.DAYS
        );
        
        // 5. 构建响应
        return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresIn(1800L)  // 30分钟
            .tokenType("Bearer")
            .userInfo(buildUserInfoVO(securityUser))
            .build();
    }
    
    @Override
    public void logout(String token) {
        // 1. 将 Token 加入黑名单
        long expiration = jwtUtil.getExpirationDate(token).getTime() - System.currentTimeMillis();
        if (expiration > 0) {
            redisTemplate.opsForValue().set(
                "blacklist:" + token,
                "logout",
                expiration,
                TimeUnit.MILLISECONDS
            );
        }
        
        // 2. 删除 Refresh Token
        Long userId = jwtUtil.getUserIdFromToken(token);
        redisTemplate.delete("refresh_token:" + userId);
    }
    
    @Override
    public LoginResponse refreshToken(String refreshToken) {
        // 1. 验证 Refresh Token
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new TokenInvalidException("Refresh Token 无效");
        }
        
        // 2. 获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        
        // 3. 验证 Redis 中的 Refresh Token
        String storedToken = redisTemplate.opsForValue().get("refresh_token:" + userId);
        if (!refreshToken.equals(storedToken)) {
            throw new TokenInvalidException("Refresh Token 已失效");
        }
        
        // 4. 查询用户信息
        SecurityUser securityUser = (SecurityUser) 
            userDetailsService.loadUserByUserId(userId);
        
        // 5. 生成新 Token
        String newAccessToken = jwtUtil.generateAccessToken(
            userId,
            securityUser.getUser().getUsername(),
            securityUser.getRoles()
        );
        String newRefreshToken = jwtUtil.generateRefreshToken(userId);
        
        // 6. 更新 Redis 中的 Refresh Token
        redisTemplate.opsForValue().set(
            "refresh_token:" + userId,
            newRefreshToken,
            7,
            TimeUnit.DAYS
        );
        
        return LoginResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .expiresIn(1800L)
            .tokenType("Bearer")
            .userInfo(buildUserInfoVO(securityUser))
            .build();
    }
}
```

#### Step 12: 创建 AuthController
- **操作**: 对外暴露认证接口
- **文件**: `src/main/java/com/wheatmall/authadmin/controller/AuthController.java`
- **代码**:

```java
@RestController
@RequestMapping("/auth")
@Validated
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "使用用户名密码登录")
    public R<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return R.ok(authService.login(request));
    }
    
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "退出登录并使 Token 失效")
    public R<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return R.ok();
    }
    
    @PostMapping("/refresh")
    @Operation(summary = "刷新 Token", description = "使用 Refresh Token 换取新的 Access Token")
    public R<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return R.ok(authService.refreshToken(request.getRefreshToken()));
    }
    
    @GetMapping("/current")
    @Operation(summary = "获取当前用户", description = "获取当前登录用户信息")
    public R<UserInfoVO> getCurrentUser() {
        return R.ok(authService.getCurrentUser());
    }
}
```

#### Step 13: 创建测试控制器（演示权限注解）
- **操作**: 创建演示接口展示 RBAC 权限控制
- **文件**: `src/main/java/com/wheatmall/authadmin/controller/DemoController.java`

```java
@RestController
@RequestMapping("/demo")
@Tag(name = "权限演示", description = "演示 RBAC 权限控制")
public class DemoController {
    
    @GetMapping("/public")
    @Operation(summary = "公开接口")
    public R<String> publicApi() {
        return R.ok("这是一个公开接口，无需认证");
    }
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "用户接口")
    public R<String> userApi() {
        return R.ok("需要 USER 角色");
    }
    
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "管理员接口")
    public R<String> adminApi() {
        return R.ok("需要 ADMIN 角色");
    }
    
    @PostMapping("/user/create")
    @PreAuthorize("hasAuthority('user:create')")
    @Operation(summary = "创建用户")
    public R<String> createUser() {
        return R.ok("需要 user:create 权限");
    }
    
    @GetMapping("/user/view")
    @PreAuthorize("hasAnyAuthority('user:view', 'user:admin')")
    @Operation(summary = "查看用户")
    public R<String> viewUser() {
        return R.ok("需要 user:view 或 user:admin 权限");
    }
}
```

---

### Phase 4: 异常处理和全局配置 (预计 30 分钟)

#### Step 14: 创建全局异常处理
- **操作**: 统一处理认证和授权异常
- **文件**: `src/main/java/com/wheatmall/authadmin/exception/GlobalExceptionHandler.java`

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    // Token 无效
    @ExceptionHandler(TokenInvalidException.class)
    public R<Void> handleTokenInvalidException(TokenInvalidException e) {
        return R.error(401, e.getMessage());
    }
    
    // 认证失败
    @ExceptionHandler(BadCredentialsException.class)
    public R<Void> handleBadCredentialsException(BadCredentialsException e) {
        return R.error(401, "用户名或密码错误");
    }
    
    // 权限不足
    @ExceptionHandler(AccessDeniedException.class)
    public R<Void> handleAccessDeniedException(AccessDeniedException e) {
        return R.error(403, "权限不足，无法访问");
    }
    
    // 参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return R.error(400, message);
    }
}
```

#### Step 15: 创建自定义异常类
- **文件**: `src/main/java/com/wheatmall/authadmin/exception/TokenInvalidException.java`

```java
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }
}
```

#### Step 16: 配置 application.yml
- **文件**: `src/main/resources/application.yml`

```yaml
server:
  port: 8092

spring:
  application:
    name: wheatmall-auth-admin
  
  # Nacos 配置
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: public
        group: DEFAULT_GROUP
  
  # Redis 配置
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      timeout: 10s
      lettuce:
        pool:
          max-active: 8
          max-idle: 8
          min-idle: 0
  
  # 数据库配置
  datasource:
    url: jdbc:mysql://localhost:3306/wheatmall_auth?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

# JWT 配置
jwt:
  secret: your-256-bit-secret-your-256-bit-secret  # 密钥（至少32字符）
  access-token-expiration: 1800  # Access Token 过期时间（秒）30分钟
  refresh-token-expiration: 604800  # Refresh Token 过期时间（秒）7天

# MyBatis
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

---

### Phase 5: 数据访问层 (预计 30 分钟)

#### Step 17: 创建实体类
- **文件**:
  - `src/main/java/com/wheatmall/authadmin/entity/SysUser.java`
  - `src/main/java/com/wheatmall/authadmin/entity/SysRole.java`
  - `src/main/java/com/wheatmall/authadmin/entity/SysPermission.java`

```java
@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

#### Step 18: 创建 Mapper 接口
- **文件**: `src/main/java/com/wheatmall/authadmin/mapper/UserMapper.java`

```java
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
    
    @Select("SELECT r.role_code FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectRolesByUserId(Long userId);
    
    @Select("SELECT p.perm_code FROM sys_permission p " +
            "INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
            "INNER JOIN sys_user_role ur ON rp.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectPermissionsByUserId(Long userId);
    
    SysUser selectByUsername(@Param("username") String username);
}
```

#### Step 19: 添加 MyBatis Plus 依赖
- **文件**: `pom.xml`
- **添加**:

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    <version>3.5.5</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

---

### Phase 6: 单元测试 (预计 30 分钟)

#### Step 20: 创建 JwtUtil 测试
- **文件**: `src/test/java/com/wheatmall/authadmin/security/jwt/JwtUtilTest.java`

```java
@ExtendWith(MockitoExtension.class)
class JwtUtilTest {
    
    private JwtUtil jwtUtil;
    
    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.setSecret("test-secret-test-secret-test-secret");
        jwtUtil.setAccessTokenExpiration(1800);
    }
    
    @Test
    @DisplayName("测试生成和解析Token")
    void testGenerateAndParseToken() {
        // 生成 Token
        String token = jwtUtil.generateAccessToken(1L, "admin", List.of("ADMIN"));
        assertNotNull(token);
        
        // 解析 Token
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        
        assertEquals(1L, userId);
        assertEquals("admin", username);
    }
    
    @Test
    @DisplayName("测试验证有效Token")
    void testValidateValidToken() {
        String token = jwtUtil.generateAccessToken(1L, "admin", List.of("ADMIN"));
        assertTrue(jwtUtil.validateToken(token));
    }
    
    @Test
    @DisplayName("测试验证过期Token")
    void testValidateExpiredToken() {
        // 设置极短过期时间
        jwtUtil.setAccessTokenExpiration(-1);
        String token = jwtUtil.generateAccessToken(1L, "admin", List.of("ADMIN"));
        assertFalse(jwtUtil.validateToken(token));
    }
}
```

#### Step 21: 创建 AuthService 测试
- **文件**: `src/test/java/com/wheatmall/authadmin/service/AuthServiceTest.java`

```java
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @Mock
    private StringRedisTemplate redisTemplate;
    
    @InjectMocks
    private AuthServiceImpl authService;
    
    @Test
    @DisplayName("测试登录成功")
    void testLoginSuccess() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");
        
        SecurityUser securityUser = createMockSecurityUser();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(securityUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateAccessToken(any(), any(), any())).thenReturn("mock-access-token");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("mock-refresh-token");
        
        // When
        LoginResponse response = authService.login(request);
        
        // Then
        assertNotNull(response);
        assertEquals("mock-access-token", response.getAccessToken());
        assertEquals("mock-refresh-token", response.getRefreshToken());
    }
}
```

---

### Phase 7: 其他服务集成 (预计 30 分钟)

#### Step 22: 将公共组件提取到 wheatmall-common
- **操作**: 让其他服务也能使用JWT验证
- **文件**: `wheatmall-common` 模块
- **迁移组件**:
  - JwtUtil → common 模块
  - JwtAuthenticationFilter → common 模块
  - SecurityUser → common 模块

#### Step 23: Order/Product 模块集成
- **操作**: 在其他模块启用JWT认证
- **文件**: `wheatmall-order/src/main/java/com/wheatmall/order/config/SecurityConfig.java`

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class OrderSecurityConfig {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/order/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

---

### Phase 8: 测试验证 (预计 30 分钟)

#### Step 24: 运行测试

```bash
# 运行所有测试
cd /Users/nidazhong/MyWork/wheatmall-2026/backend
mvnd test

# 只测试 auth-admin 模块
mvnd test -pl wheatmall-auth-admin
```

#### Step 25: 接口测试

```bash
# 1. 登录
curl -X POST http://localhost:8092/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'

# 响应示例：
# {
#   "code": 200,
#   "msg": "success",
#   "data": {
#     "accessToken": "eyJhbGc...",
#     "refreshToken": "eyJhbGc...",
#     "expiresIn": 1800,
#     "tokenType": "Bearer"
#   }
# }

# 2. 访问需要认证的接口
curl -X GET http://localhost:8092/demo/user \
  -H "Authorization: Bearer {access_token}"

# 3. 登出
curl -X POST http://localhost:8092/auth/logout \
  -H "Authorization: Bearer {access_token}"

# 4. 刷新 Token
curl -X POST http://localhost:8092/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"{refresh_token}"}'
```

---

## 三、文件清单

### 核心文件 (25个)

| 序号 | 文件路径 | 说明 |
|------|----------|------|
| 1 | `pom.xml` | Maven 配置 |
| 2 | `README.md` | 模块说明 |
| 3 | `doc/JWT_AUTH_DEVELOPMENT_PLAN.md` | 本规划文档 |
| 4 | `doc/sql/rbac_schema.sql` | 数据库脚本 |
| 5 | `src/main/resources/application.yml` | 配置文件 |
| 6 | `src/main/java/.../AuthAdminApplication.java` | 启动类 |
| 7 | `src/main/java/.../config/SecurityConfig.java` | Security配置 |
| 8 | `src/main/java/.../security/jwt/JwtUtil.java` | JWT工具类 |
| 9 | `src/main/java/.../security/filter/JwtAuthenticationFilter.java` | JWT过滤器 |
| 10 | `src/main/java/.../security/service/UserDetailsServiceImpl.java` | 用户加载服务 |
| 11 | `src/main/java/.../security/entity/SecurityUser.java` | Security用户封装 |
| 12 | `src/main/java/.../service/AuthService.java` | 认证服务接口 |
| 13 | `src/main/java/.../service/impl/AuthServiceImpl.java` | 认证服务实现 |
| 14 | `src/main/java/.../controller/AuthController.java` | 认证控制器 |
| 15 | `src/main/java/.../controller/DemoController.java` | 演示控制器 |
| 16 | `src/main/java/.../dto/LoginRequest.java` | 登录请求DTO |
| 17 | `src/main/java/.../dto/LoginResponse.java` | 登录响应DTO |
| 18 | `src/main/java/.../vo/UserInfoVO.java` | 用户信息VO |
| 19 | `src/main/java/.../entity/SysUser.java` | 用户实体 |
| 20 | `src/main/java/.../entity/SysRole.java` | 角色实体 |
| 21 | `src/main/java/.../entity/SysPermission.java` | 权限实体 |
| 22 | `src/main/java/.../mapper/UserMapper.java` | 用户Mapper |
| 23 | `src/main/java/.../exception/GlobalExceptionHandler.java` | 全局异常处理 |
| 24 | `src/main/java/.../exception/TokenInvalidException.java` | Token异常 |
| 25 | `src/test/java/.../security/jwt/JwtUtilTest.java` | JWT测试 |

---

## 四、时间预估

| Phase | 阶段 | 预计时间 |
|-------|------|----------|
| Phase 1 | 基础环境准备 | 30分钟 |
| Phase 2 | 核心组件开发 | 60分钟 |
| Phase 3 | 业务层开发 | 45分钟 |
| Phase 4 | 异常处理和配置 | 30分钟 |
| Phase 5 | 数据访问层 | 30分钟 |
| Phase 6 | 单元测试 | 30分钟 |
| Phase 7 | 其他服务集成 | 30分钟 |
| Phase 8 | 测试验证 | 30分钟 |
| **总计** | | **约5小时** |

---

## 五、注意事项

### 5.1 安全建议

1. **密钥管理**: JWT 密钥应通过环境变量或配置中心注入，不要硬编码
2. **密码加密**: 必须使用 BCrypt 等强哈希算法，禁止明文存储
3. **Token 存储**: 前端应将 Token 存储在 httpOnly cookie 或内存中，避免 XSS 攻击
4. **HTTPS**: 生产环境必须使用 HTTPS 传输 Token

### 5.2 Redis 策略

```
# Token 黑名单 (登出时使用)
Key: blacklist:{token}
Value: logout
TTL: Token剩余过期时间

# Refresh Token 存储
Key: refresh_token:{userId}
Value: {refresh_token}
TTL: 7天
```

### 5.3 性能优化

1. **用户权限缓存**: 可将用户权限缓存到 Redis，减少数据库查询
2. **Token 解析优化**: 考虑使用 JWT 缓存或本地线程缓存
3. **数据库索引**: 确保 username、role_code、perm_code 字段有索引

---

## 六、后续扩展建议

1. **多租户支持**: 添加 tenant_id 字段实现多租户
2. **OAuth2.0**: 支持第三方登录（微信、QQ、GitHub）
3. **单点登录**: 集成 CAS 或 OAuth2 实现 SSO
4. **登录审计**: 记录登录日志（IP、时间、设备）
5. **限流防刷**: 添加登录失败次数限制（防暴力破解）
6. **Token 续期**: 实现无感刷新 Token 机制

---

**文档结束**

> 提示：实际开发时请根据具体需求调整实现细节。
