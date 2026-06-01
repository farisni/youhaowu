# AI 操作规则 (AI Operating Rules)

本文档记录了与 AI 助手协作时的操作规范和规则。

## 核心规则

### 1. 测试验证规则

**规则描述：**
Controller 层测试统一使用 `backend/intergre-py-test/` 下的 pytest + requests 方案，不写 Java 单元测试。

**具体操作：**
1. 新增或修改 Controller 方法后，在 `backend/intergre-py-test/` 编写对应的 pytest 测试
2. 测试文件命名 `test_<模块>.py`，如 `test_category.py`
3. 完成后运行：`cd backend/intergre-py-test && python3 -m pytest test_<模块>.py -v`
4. **仅当所有测试通过后**，才向用户报告成功

详见规则 #6「集成测试规则」。

### 2. Git 提交规则

**规则描述：**
**严禁在用户验收前提交代码！** 必须等用户明确说「提交」「commit」后，方可执行。

**具体操作：**
- **用户未要求「提交」时，禁止 `git commit`，只允许 `git add`**
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

### 4. TODO 文件规则

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

### 5. 代码注释规则

**规则描述：**
方法体内代码超过 15 行时，方法签名上方必须加 Javadoc，且方法内关键逻辑块前加行内注释。

**具体要求：**
- 方法体超过 15 行（不含空行和只有大括号的行），必须在方法签名上方加 Javadoc 注释
- Javadoc 写清楚方法做的事（一句话即可，复杂方法可补充关键步骤）
- 方法内关键步骤处加行内注释，格式统一用 `//  xxxxx`（双斜杠+两个空格+中文）
- 15 行以内的方法也必须加 Javadoc（Controller 编码规范铁律第 7 条），行内注释不强制

**示例：**
```java
public void complexMethod() {
    //  1. 校验入参
    validateParams(dto);

    //  2. 查询关联数据
    List<RelationEntity> relations = relationMapper.selectList(...);

    //  3. 构建 VO 并填充关联信息
    List<XxxVO> vos = entities.stream().map(e -> {
        XxxVO vo = new XxxVO();
        //  ...
        return vo;
    }).collect(Collectors.toList());

    //  4. 保存并返回
    mapper.batchInsert(vos);
}
```

**注意：**
- 不要每行都加注释，在逻辑分界点加即可

### 6. 集成测试规则

**规则描述：**
每个新增或修改的 Controller 方法，必须在 `backend/intergre-py-test/` 目录下编写对应的 pytest + requests 集成测试。

**具体要求：**
- 测试文件命名：`test_<模块>.py`（如 `test_category.py`）
- 使用 pytest + requests，不依赖 Spring 上下文
- 覆盖正常返回、边界条件、参数校验三个维度
- 测试文件中需包含 README.md，写清楚运行命令
- 完成后必须实际运行验证全部通过

**文件位置：**
```
backend/intergre-py-test/
├── README.md          # 项目级说明
├── test_category.py   # 分类模块测试
├── test_xxx.py        # 其他模块测试
```

**运行命令：**
```bash
cd backend/intergre-py-test
python3 -m pytest test_xxx.py -v
```

### 7. Jenkins 自动部署规则

**规则描述：**
每次代码推送（git push）后，自动触发对应模块的 Jenkins Job 进行部署。

**具体操作：**
- 推送代码后，自动执行 Jenkins CLI 触发部署
- 根据改动的模块选择对应参数
- 部署命令：`java -jar ~/jenkins-cli.jar -s http://192.168.8.112:8070 -auth faris:$JENKINS_TOKEN build youhaowu -p MODULE=<模块名>`
- 如改动 `youhaowu-backend/youhaowu-product` → `MODULE=youhaowu-product`
- 如改动 `youhaowu-backend/youhaowu-gateway` → `MODULE=youhaowu-gateway`
- 如改动前端 `youhaowu-admin-vue` → 暂时跳过（前端未接入）

**环境变量要求：**
- 需在环境变量中设置 `JENKINS_TOKEN`

### 8. DEV_TIMELINE 日期校验规则

**规则描述：**
每次提交前必须确保 DEV_TIMELINE.md 中的条目挂在正确的日期标题下。

**具体操作：**
- 提交前检查文件末尾日期标题是否为当天日期
- 如果不是，先创建 `## YYYY-MM-DD` 标题再追加条目
- 禁止将新条目追加到历史日期区块下方
- 验证命令：`tail -5 DEV_TIMELINE.md`
