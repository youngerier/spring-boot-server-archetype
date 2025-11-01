# App-Biz 分包逻辑与最佳实践指南

## 概述

`app-biz` 模块是白标系统的业务逻辑层，负责实现具体的业务功能。该模块采用了清晰的分包策略和设计模式，确保代码的可维护性、可扩展性和可测试性。

## 模块结构

### 子模块划分

`app-biz` 按业务领域划分为以下子模块：

```
app-biz/
├── app-biz-user/          # 用户管理业务
├── app-biz-system/        # 系统配置业务
├── app-biz-file/          # 文件管理业务
└── app-biz-cdd/          # 客户尽职调查业务
```

### 分包逻辑

每个子模块都遵循统一的分包结构：

```
com.qbit.white.label.biz.{domain}/
├── constant/              # 常量定义
├── config/               # 配置类        - 这里最好放到上层模块
├── aspect/               # 切面类        - 这里最好放到上层模块
├── dal/                  # 数据访问层
│   ├── entity/           # 实体类
│   ├── repository/       # Repository接口
│   └── mapper/           # MyBatis Mapper接口
├── mapstruct/            # 对象转换器
└── service/              # 业务服务层
    └── impl/             # 服务实现类
```

## 设计模式与架构原则

### 1. 分层架构

采用经典的三层架构模式：

- **表示层（API）**: 由 `app-api` 模块提供接口定义
- **业务逻辑层（Service）**: 由 `app-biz` 模块实现具体业务逻辑
- **数据访问层（DAL）**: 封装数据库操作

### 2. 依赖注入模式

所有服务类都使用 Spring 的依赖注入：

```java
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    
    public UserServiceImpl(UserRepository userRepository, 
                          AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }
}
```

### 3. Repository 模式

数据访问层使用 Repository 模式，基于 MyBatis-Flex：

```java
public interface UserRepository extends BaseMapper<User> {
    default User getUserByUsername(String username) {
        // 自定义查询逻辑
    }
}
```

### 4. DTO 转换模式

使用 MapStruct 进行对象转换：

```java
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    
    UserDTO convertToUserDTO(User user);
    User convertToUser(UserDTO userDTO);
}
```

### 5. 切面编程（AOP）

使用 AOP 处理横切关注点，如日志记录：

```java
@Component
public class LogFileStorageAspect implements FileStorageAspect {
    // 文件操作日志记录
}
```

## 最佳实践

### 1. 包命名规范

- 使用小写字母和点分隔符
- 按业务领域划分包结构
- 保持包名简洁明了

### 2. 类命名规范

- **实体类**: 使用名词，如 `User`, `Account`
- **服务类**: 使用 `Service` 后缀，如 `UserService`
- **实现类**: 使用 `Impl` 后缀，如 `UserServiceImpl`
- **Repository**: 使用 `Repository` 后缀
- **Mapper**: 使用 `Mapper` 后缀
- **转换器**: 使用 `Converter` 或 `Convertor` 后缀

### 3. 代码组织原则

#### 单一职责原则
每个类只负责一个业务领域的功能：

```java
// 好的实践：专注于用户管理
@Service
public class UserServiceImpl implements UserService {
    // 只处理用户相关业务
}

// 好的实践：专注于账户管理
@Service
public class AccountServiceImpl implements AccountService {
    // 只处理账户相关业务
}
```

#### 依赖倒置原则
依赖抽象而不是具体实现：

```java
@Service
public class UserServiceImpl implements UserService {
    // 依赖接口而不是具体实现
    private final UserRepository userRepository;
    private final AccountService accountService;
}
```

#### 开闭原则
对扩展开放，对修改关闭：

```java
// 通过接口扩展功能
public interface FileService {
    FileBaseDTO uploadFile(MultipartFile file, UploadFileRequest request);
    FileBaseDTO uploadFileHash(MultipartFile file, UploadFileRequest request);
    FileBaseDTO uploadImage(MultipartFile file, UploadFileRequest request);
}
```

### 4. 数据访问层最佳实践

#### Repository 接口设计

```java
public interface UserRepository extends BaseMapper<User> {
    // 提供默认实现的查询方法
    default User getUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        UserTableRefs userTableRefs = UserTableRefs.user;
        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(userTableRefs)
                .where(userTableRefs.nickname.eq(username));
        return selectOneByQuery(queryWrapper);
    }
}
```

#### 复杂查询构建

```java
default QueryWrapper buildQueryWrapper(AccountPageRequest req) {
    QueryWrapper wrapper = QueryWrapperHelper.withOrder(req);
    wrapper.select(/* 指定查询字段 */)
           .from(/* 主表 */)
           .leftJoin(/* 关联表 */)
           .where(/* 查询条件 */);
    return wrapper;
}
```

### 5. 服务层最佳实践

#### 事务管理

```java
@Override
@Transactional
public UserDTO register(UserRegisterRequest request, Long tenantId) {
    // 业务逻辑处理
    // 自动事务管理
}
```

#### 异常处理

```java
@Override
public UserDTO queryUserById(Long id) {
    AssertUtils.notNull(id, "用户ID不能为空");
    User user = userRepository.selectOneById(id);
    if (user == null) {
        throw new CustomerException("用户不存在");
    }
    return UserConverter.INSTANCE.convertToUserDTO(user);
}
```

#### 参数验证

```java
@Component
public class ValidationUtils {
    public void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }
}
```

### 6. 配置管理最佳实践

#### 配置类设计

```java
@Configuration
@Import({FileStorageAutoConfiguration.class, SpringFileStorageProperties.class})
public class FileStorageConfig {
    // 配置文件存储相关Bean
}
```

#### 常量管理

```java
public class CodeConstant {
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String INVALID_PASSWORD = "INVALID_PASSWORD";
    // 集中管理业务常量
}
```

### 7. 切面编程最佳实践

#### 日志切面

```java
@Component
public class LogFileStorageAspect implements FileStorageAspect {
    @Override
    public FileInfo uploadAround(UploadAspectChain chain, ...) {
        log.info("开始上传文件: {}", fileInfo.getOriginalFilename());
        try {
            FileInfo result = chain.next();
            log.info("文件上传成功: {}", result.getUrl());
            return result;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw e;
        }
    }
}
```

## 模块间依赖关系

### 依赖层次

```
app-biz-* 模块
    ↓ 依赖
app-api-* 模块 (接口定义)
    ↓ 依赖
app-infra-business (基础设施)
    ↓ 依赖
app-core (核心工具)
```

### 模块间通信

- 通过接口进行模块间通信
- 避免循环依赖
- 使用事件驱动进行解耦

## 测试策略

### 单元测试

```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void testQueryUserById() {
        // 测试逻辑
    }
}
```

### 集成测试

```java
@SpringBootTest
@Transactional
class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    
    @Test
    void testUserRegistration() {
        // 集成测试逻辑
    }
}
```

## 性能优化建议

### 1. 数据库查询优化

- 使用合适的索引
- 避免 N+1 查询问题
- 使用分页查询处理大数据集

### 2. 缓存策略

- 对频繁查询的数据使用缓存
- 合理设置缓存过期时间
- 使用分布式缓存处理集群环境

### 3. 异步处理

- 对耗时操作使用异步处理
- 使用消息队列处理批量任务

## 安全考虑

### 1. 数据验证

- 对所有输入数据进行验证
- 使用白名单而不是黑名单
- 防止 SQL 注入攻击

### 2. 权限控制

- 实现细粒度的权限控制
- 使用角色基础的访问控制（RBAC）
- 记录敏感操作日志

### 3. 数据加密

- 对敏感数据进行加密存储
- 使用安全的加密算法
- 妥善管理加密密钥

## 监控与日志

### 1. 日志记录

- 记录关键业务操作
- 使用结构化日志格式
- 设置合适的日志级别

### 2. 性能监控

- 监控关键业务指标
- 设置性能阈值告警
- 定期进行性能分析

## 总结

`app-biz` 模块的分包逻辑遵循了清晰的业务领域划分和分层架构原则。通过合理的设计模式应用、最佳实践遵循和规范的代码组织，确保了系统的可维护性、可扩展性和可测试性。

在实际开发中，应该：

1. 严格遵循分包规范和命名约定
2. 合理应用设计模式，避免过度设计
3. 注重代码质量和测试覆盖率
4. 持续优化性能和安全性
5. 保持良好的文档和注释

这些实践将有助于构建高质量、可维护的企业级应用系统。