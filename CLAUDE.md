# AI 操作规则 (AI Operating Rules)

本文档记录了与 AI 助手协作时的操作规范和规则。

## 核心规则

### 1. 测试验证规则

**规则描述：**
每次完成用户提出的需求后，必须执行测试验证，确保代码正确性。

**具体操作：**
1. 需求完成后，运行命令：`mvnd test`（或 `mvn test`，视环境而定）
2. 等待所有测试执行完毕
3. **仅当所有测试通过后**，才向用户报告成功
4. 如果有测试失败，必须修复后才能报告完成

**示例流程：**
```
用户提出需求 → AI实现需求 → 运行 mvnd test → 全部通过 → 报告成功
                                    ↓
                              有失败 → 修复问题 → 重新测试 → 通过 → 报告成功
```

### 2. Git 提交规则

**规则描述：**
未经用户明确要求，不得擅自执行 `git commit` 和 `git push` 操作。

**具体操作：**
- **只有在用户明确要求时，才能执行 `git commit`**
- **只有在用户明确说"push"或"推送"时，才能执行 `git push`**
- 完成需求后，先报告完成情况，询问用户是否需要提交
- 告知用户哪些文件发生了变更，等待用户确认后再提交

#### 提交信息格式

**格式：** `<type>(<scope>): <中文描述>`

**type 类型：**

| type | 含义 |
|------|------|
| `feat` | 新功能 |
| `fix` | 修 bug |
| `docs` | 文档 |
| `style` | 格式 |
| `refactor` | 重构 |
| `perf` | 性能优化 |
| `test` | 测试 |
| `build` | 构建/依赖 |

**示例：**
```
feat(login): 添加登录功能
fix(cart): 修复数量计算错误
```

**注意：**
- scope 使用模块或功能简写（如 `login`、`cart`、`product`、`order`）
- 描述使用中文，简洁明了
- 每次代码变更后，在 `DEV_TIMELINE.md` 中添加一条带日期的简要记录

### 3. 文档记录规则

**规则描述：**
每次完成需求后，必须在 README.md 中记录本次工作内容，确保项目文档不丢失。

**具体要求：**
- README.md 仅保留项目结构和技术栈等概述性内容
- 开发记录统一维护在 `DEV_TIMELINE.md`，以日期为标题记录每项变更
- 每条记录格式：`- <type>: <简要描述>`，type 遵循 Git 提交规范

**示例格式：**
```markdown
## 2026-05-22

- feat: 添加登录功能
- fix: 修复购物车数量计算错误
```

#### DEV_TIMELINE.md 开发时间线

**规则描述：**
每次代码变更后，必须在项目根目录的 `DEV_TIMELINE.md` 中追加一条带日期的简要记录。

**具体操作：**
- 格式：以日期为二级标题 `## YYYY-MM-DD`，下方为变更列表
- **每次 git commit 时，必须在同一 commit 中包含 DEV_TIMELINE.md 的更新**
- **新条目必须追加在当前日期区块的最后一行，不允许插入中间位置**
- 每条变更一行，简洁明了
- 同一天的多条记录合并到同一个日期标题下
- 如果当天没有日期标题，则新建 `## YYYY-MM-DD` 标题

**示例：**
```markdown
## 2026-05-22

- feat: 添加登录功能
- fix: 修复购物车数量计算错误
```

**不记录的内容：** CSS 微调（间距、尺寸、位置）、logo/图片资源替换、空白行、格式修正等无功能影响的细小变动。

### 4. 需求拆分和步骤记录规则

**规则描述：**
接到用户需求后，先将其拆分为多个可执行的步骤，并记录大致的执行计划。

**具体操作：**
1. 分析需求，拆分成逻辑清晰的若干步骤
2. 在 README.md 的 `## 开发记录` 中记录需求标题和拆分后的步骤
3. 每完成一个步骤，在对应步骤后标记完成状态
4. 如遇到需求变更或步骤调整，及时更新记录

**示例格式：**
```markdown
### 2026-02-17: 实现XXX功能

**执行步骤：**
1. [x] 分析需求，确定技术方案
2. [x] 添加相关依赖
3. [x] 实现核心功能代码
4. [x] 编写单元测试
5. [x] 运行测试验证
6. [ ] 更新文档（待完成）

**主要变更：**
- 添加/修改了某个功能
```

**注意事项：**
- 步骤不需要过于详细，但要能反映整体工作流程
- 使用 `[x]` 标记已完成，`[ ]` 标记未完成
- 复杂的子任务可以进一步拆分
- **README.md 记录大致步骤，auth-admin-plan.md 等详细文档记录细节步骤，但也不是事无巨细**



### 5. TODO 文件规则

| 文件 | 作用域 | 说明 |
|------|--------|------|
| `TODO.md` | 后端 / 全项目 | 后端功能、安全、性能、扩展待办 |
| `frontend/TODO.md` | 前端 Admin | 前端页面、组件、路由待办 |

**严禁：** 把前端待办写到根目录 `TODO.md`，也不要把后端待办写到 `frontend/TODO.md`。

## 技术栈特定规则

### Maven 项目
- 优先使用 `mvnd`（Maven Daemon）进行构建和测试，速度更快
- 如果 `mvnd` 不可用，则使用 `mvn`
- 测试命令：`mvnd test` 或 `mvn test`

### Spring Boot 项目
- 修改代码后必须验证编译是否通过
- 新增依赖后必须验证依赖是否正确引入
- 单元测试使用 JUnit 5 + Mockito

### Java 对象命名规范

| 缩写 | 全称 | 中文含义 | 使用场景 | 说明 |
|------|------|----------|----------|------|
| DTO | Data Transfer Object | 数据传输对象 | Service ↔ Controller | 层间数据传输 |
| VO | View Object | 视图对象 | Controller ↔ 前端 | 返回给前端展示 |
| DO/PO | Domain/Persistent Object | 领域/持久化对象 | DAO ↔ Database | 数据库表映射 |
| BO | Business Object | 业务对象 | Service 层 | 封装业务逻辑 |
| Query | Query Object | 查询对象 | Controller → Service | 封装查询参数 |
| POJO | Plain Old Java Object | 简单Java对象 | 任意 | 普通Java对象 |

**使用原则：**
- Controller 返回前端统一使用 VO
- Service 层接收参数使用 DTO 或 Query
- 与数据库交互使用 DO/PO
- 复杂业务逻辑封装在 BO 中



### 模块分层架构规范（阿里手册 + 国内电商经验）

**真正标配：**

| 层 | 类 | 职责 | 说明 |
|------|------|------|------|
| Controller | `controller/XxxController.java` | 接收 VO/Query，返回 VO | 不碰 Entity |
| Service 接口 | `service/XxxService.java` | 接口定义 | 业务契约 |
| Service 实现 | `service/impl/XxxServiceImpl.java` | 业务实现 + 调用 Mapper | 核心逻辑 |
| Mapper | `mapper/XxxMapper.java` | `extends BaseMapper<XxxEntity>` | 数据库访问 |
| Entity | `entity/XxxEntity.java` | 纯表映射，DO | 数据库表一一对应 |

**三不一要：**

- Entity **不**进 Controller
- Service **不**直接返回 Entity 给 Controller
- Mapper **不**写业务逻辑
- **要**有 DTO/VO 分层

### Controller 编码规范

**示例模板（CategoryController）：**

```java
@RestController
@RequestMapping(ServiceUris.PRODUCT_SERVICE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类树
     * 返回三级分类树形结构，一级分类包含子分类，子分类嵌套孙分类
     */
    @GetMapping(ServiceUris.ProductCategory.LIST_TREE)
    public R<List<CategoryVO>> list() {
        return R.ok(categoryService.listWithTree());
    }
}
```

**六条铁律：**

1. 类级路由用 `ServiceUris` 根路径常量，不硬编码字符串
2. Service 注入用 `@Autowired` 字段注入
3. 方法路由用具体 `@GetMapping`/`@PostMapping` + `ServiceUris` 内部类常量
4. 返回统一用 `R<T>` 包装，不返回裸数据
5. Controller 极薄，一行转发，不写业务逻辑
6. 不直接返回 Entity，只接触 VO
7. 每个方法必须有 Javadoc 注释，说明接口用途和返回值


**按需追加：**

| 类 | 包路径 | 场景 |
|------|---------|------|
| DTO | `dto/XxxDTO.java` | Service ↔ Controller 传输 |
| VO | `vo/XxxVO.java` | Controller → 前端展示 |
| Query | `query/XxxQuery.java` | 前端 → Controller 查询参数 |
| BO | `bo/XxxBO.java` | Service 层复杂业务封装 |


## 沟通规范

### 测试报告格式
测试成功后，应按以下格式报告：

```
**测试成功！** ✅

**测试结果：**
- 模块A: X个测试通过
- 模块B: Y个测试通过
- **共计：Z个测试，全部通过** ✓
```

### 完成确认
需求完成后应询问用户是否需要：
1. 提交代码（git commit）
2. 推送代码（git push）- **必须等待用户明确同意**
3. 其他后续操作

## 更新记录

| 日期 | 更新内容 | 更新人 |
|------|----------|--------|
| 2026-02-17 | 初始版本，添加测试验证规则、Git提交规则 | AI Assistant |
| 2026-02-17 | 添加文档记录规则 | AI Assistant |
| 2026-02-17 | 添加需求拆分和步骤记录规则 | AI Assistant |
| 2026-02-17 | 添加Java对象命名规范 | AI Assistant |
| 2026-05-22 | 添加 Git 提交信息格式规范 + DEV_TIMELINE.md 规则 | AI Assistant |

---

**注意：** 本文档由 AI 助手维护，如有新的规则需求，请告知 AI 助手更新此文档。
