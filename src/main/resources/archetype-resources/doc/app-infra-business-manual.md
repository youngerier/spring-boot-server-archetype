# app-infra-business 模块操作手册

## 概述

`app-infra-business` 是白标服务器项目的核心业务基础设施模块，提供了通用的业务功能和工具类，包括异常处理、JWT认证、数据验证、国际化支持等核心功能。

## 模块结构

```
app-infra-business/
├── src/main/java/com/qbit/white/label/infra/business/
│   ├── AppType.java                    # 应用类型枚举
│   ├── AssertUtils.java               # 断言工具类
│   ├── CustomerCodeUtils.java         # 客户代码工具类
│   ├── DefaultExceptionCode.java      # 默认异常码
│   ├── DescriptiveEnum.java           # 描述性枚举接口
│   ├── ExceptionCode.java             # 异常码接口
│   ├── JwtService.java                # JWT服务接口
│   ├── JwtUser.java                   # JWT用户实体
│   ├── PrincipalType.java             # 主体类型枚举
│   ├── Result.java                    # 统一返回结果类
│   ├── base/                          # 基础类
│   │   ├── BaseEntity.java            # 基础实体类
│   │   └── HttpServletRequestUtils.java # HTTP请求工具类
│   ├── constants/                     # 常量类
│   │   └── WebConstants.java          # Web常量
│   ├── context/                       # 上下文管理
│   │   ├── AppContextInitializer.java # 应用上下文初始化器
│   │   ├── AppTypeContext.java        # 应用类型上下文
│   │   ├── CurrentUserContext.java    # 当前用户上下文
│   │   └── SpringApplicationContextUtils.java # Spring上下文工具类
│   ├── encryptor/                     # 加密工具
│   │   └── Pbkdf2PasswordUtils.java   # PBKDF2密码工具
│   ├── exception/                     # 异常处理
│   │   ├── BaseException.java         # 基础异常类
│   │   ├── CustomerException.java     # 客户异常类
│   │   ├── CustomerFactory.java       # 客户工厂类
│   │   └── SystemException.java       # 系统异常类
│   ├── handler/                       # 类型处理器
│   │   ├── AbstractJsonbTypeHandler.java # 抽象JSONB类型处理器
│   │   ├── ArrayObjectTypeHandler.java   # 数组对象类型处理器
│   │   └── MapTypeHandler.java           # Map类型处理器
│   ├── i18n/                          # 国际化支持
│   │   ├── I18nMessageUtils.java      # 国际化消息工具类
│   │   └── SpringI18nMessageSourceInitializer.java # Spring国际化消息源初始化器
│   ├── initializer/                   # 初始化器
│   │   ├── ApplicationStartedListener.java # 应用启动监听器
│   │   ├── ExceptionCodeInitializer.java   # 异常码初始化器
│   │   └── SystemInitializer.java          # 系统初始化器接口
│   ├── json/                          # JSON处理
│   │   ├── JSONUtil.java              # JSON工具类
│   │   └── SerizalizerConfig.java     # 序列化配置
│   ├── properties/                    # 配置属性
│   │   ├── BusinessProperties.java    # 业务配置属性
│   │   └── JwtProperties.java         # JWT配置属性
│   └── validator/                     # 验证器
│       └── ValidationUtils.java       # 验证工具类
```

## 核心类详解

### 1. 统一返回结果类 (Result)

`Result<T>` 类用于统一API返回格式。

#### 使用方法：

```java
// 成功返回
Result<String> success = Result.ok("操作成功");
Result<User> userResult = Result.ok(user);

// 失败返回
Result<Void> failure = Result.fail("操作失败");
Result<Void> customFailure = Result.fail("400", "参数错误");
```

#### 字段说明：
- `code`: 状态码
- `message`: 返回信息
- `data`: 返回数据
- `success`: 是否成功

### 2. 异常处理体系

#### BaseException - 基础异常类

提供统一的异常处理机制，支持异常码管理和国际化。

```java
// 简单使用
throw new BaseException("操作失败");

// 使用错误码
throw new BaseException(DefaultExceptionCode.BAD_REQUEST, "参数不合法");

// 使用占位符
throw new BaseException(MessagePlaceholder.of("用户[{0}]不存在", userId));

// 使用静态工厂方法
throw BaseException.badRequest("参数不合法");
```

#### CustomerException - 客户异常类

用于业务异常处理，支持HTTP状态码和参数化消息。

```java
// 基本使用
throw new CustomerException("USER_NOT_FOUND");

// 带参数
throw new CustomerException("USER_NOT_FOUND", new Object[]{userId});

// 指定HTTP状态码
throw new CustomerException("INVALID_PARAM", HttpStatus.BAD_REQUEST);
```

#### DefaultExceptionCode - 默认异常码

预定义的常用异常码：

- `BAD_REQUEST("400", "请求不合法")`
- `UNAUTHORIZED("401", "未认证")`
- `FORBIDDEN("403", "无权限")`
- `NOT_FOUND("404", "资源不存在")`
- `TO_MANY_REQUESTS("429", "请求过于频繁")`
- `COMMON_ERROR("500", "通用业务错误")`

### 3. JWT认证体系

#### JwtService - JWT服务接口

提供JWT令牌的生成、验证和解析功能。

```java
public interface JwtService {
    String generateToken(JwtUser username);           // 生成访问令牌
    boolean validateToken(String token);              // 验证令牌
    String extractUsername(String token);             // 提取用户名
    String generateRefreshToken(JwtUser username);    // 生成刷新令牌
    boolean validateRefreshToken(String token);       // 验证刷新令牌
    JwtUser extractRefreshTokenUsername(String token); // 从刷新令牌提取用户
    JwtUser extractUser(String token);                // 提取用户信息
    int getAccessTokenExpirationDays();               // 获取访问令牌过期天数
    int getRefreshTokenExpirationDays();              // 获取刷新令牌过期天数
}
```

#### JwtUser - JWT用户实体

```java
@Data
@Builder
public class JwtUser {
    private Long userId;                              // 用户ID
    private Long tenantId;                           // 租户ID
    private String username;                         // 用户名
    private Map<String, Object> attributes;          // 用户属性
    
    // 获取属性
    public <T> T getAttribute(String key);
    // 获取必需属性
    public <T> T requireAttribute(String key);
}
```

### 4. 上下文管理

#### CurrentUserContext - 当前用户上下文

管理当前登录用户信息。

```java
// 获取当前用户
JwtUser currentUser = CurrentUserContext.getCurrentUser();

// 获取当前用户ID
Long userId = CurrentUserContext.getCurrentUserId();

// 获取当前租户ID
Long tenantId = CurrentUserContext.getCurrentTenantId();

// 检查是否已登录
boolean isAuthenticated = CurrentUserContext.isAuthenticated();
```

#### AppTypeContext - 应用类型上下文

管理应用类型信息。

```java
// 设置应用类型
AppTypeContext.setAppType(AppType.ADMIN);

// 获取应用类型
AppType appType = AppTypeContext.appType();
```

#### SpringApplicationContextUtils - Spring上下文工具类

提供Spring容器访问功能。

```java
// 获取Bean
UserService userService = SpringApplicationContextUtils.getBean(UserService.class);

// 获取配置属性
String value = SpringApplicationContextUtils.getProperty("app.config.test");

// 发布事件
SpringApplicationContextUtils.publishEvent(new CustomEvent());
```

### 5. 断言工具类 (AssertUtils)

提供各种断言方法，用于参数验证和业务逻辑检查。

```java
// 非空检查
AssertUtils.notNull(user, "用户不能为空");

// 非空字符串检查
AssertUtils.hasText(username, "用户名不能为空");

// 集合非空检查
AssertUtils.notEmpty(userList, "用户列表不能为空");

// 条件检查
AssertUtils.isTrue(age > 0, "年龄必须大于0");

// 数字范围检查
AssertUtils.isPositive(amount, "金额必须为正数");

// 正则表达式检查
AssertUtils.matches(email, EMAIL_PATTERN, "邮箱格式不正确");
```

### 6. 验证工具类 (ValidationUtils)

基于JSR-303的对象验证。

```java
@Component
public class UserService {
    
    @Autowired
    private ValidationUtils validationUtils;
    
    public void createUser(User user) {
        // 验证用户对象
        validationUtils.validate(user);
        // 验证通过后的业务逻辑
        // ...
    }
}
```

### 7. 枚举类型

#### AppType - 应用类型枚举

```java
public enum AppType {
    ADMIN(1, "运营", "admin"),    // 运营端
    MEMBER(2, "会员", "member");  // 会员端
    
    // 根据值获取枚举
    AppType appType = AppType.of("admin");
}
```

#### PrincipalType - 主体类型枚举

```java
public enum PrincipalType {
    USERNAME(1, "用户名"),
    MOBILE_PHONE(2, "手机号码"),
    EMAIL(3, "邮箱");
}
```

### 8. 数据库类型处理器

#### AbstractJsonbTypeHandler - 抽象JSONB类型处理器

用于PostgreSQL的JSONB类型处理。

```java
public class CustomObjectTypeHandler extends AbstractJsonbTypeHandler<CustomObject> {
    @Override
    protected CustomObject getObject(String params) {
        return JSON.parseObject(params, CustomObject.class);
    }
}
```

#### ArrayObjectTypeHandler - 数组对象类型处理器

处理List<Object>与JSONB的转换。

### 9. 国际化支持

#### I18nMessageUtils - 国际化消息工具类

```java
// 获取国际化消息
String message = I18nMessageUtils.getMessage("user.not.found");

// 带参数的国际化消息
String message = I18nMessageUtils.getMessage("user.not.found", userId);

// 指定语言环境
String message = I18nMessageUtils.getMessage("user.not.found", Locale.ENGLISH);
```

### 10. HTTP工具类

#### HttpServletRequestUtils - HTTP请求工具类

```java
// 获取当前请求
HttpServletRequest request = HttpServletRequestUtils.getRequest();

// 获取当前响应
HttpServletResponse response = HttpServletRequestUtils.getResponse();

// 获取请求头
String userAgent = HttpServletRequestUtils.getHeader("User-Agent");

// 获取客户端IP
String clientIp = HttpServletRequestUtils.getClientIp();
```

## 配置属性

### BusinessProperties - 业务配置属性

```yaml
app:
  config:
    test: "测试配置"
    appType: ADMIN
```

### JwtProperties - JWT配置属性

```yaml
jwt:
  secret: "your-secret-key"
  expiration: 86400
  refresh-expiration: 604800
```

## 最佳实践

### 1. 异常处理

- 使用 `BaseException` 处理业务异常
- 使用 `CustomerException` 处理客户相关异常
- 定义自己的异常码枚举实现 `ExceptionCode` 接口
- 利用国际化支持提供多语言错误消息

### 2. 用户上下文

- 使用 `CurrentUserContext` 获取当前用户信息
- 在需要用户信息的地方进行权限检查
- 合理使用 `AppTypeContext` 区分不同应用类型的逻辑

### 3. 参数验证

- 使用 `AssertUtils` 进行快速参数检查
- 使用 `ValidationUtils` 进行复杂对象验证
- 结合JSR-303注解定义验证规则

### 4. 返回结果

- 统一使用 `Result<T>` 包装API返回结果
- 成功时使用 `Result.ok()`
- 失败时使用 `Result.fail()`

### 5. 数据库操作

- 使用相应的TypeHandler处理复杂数据类型
- 继承 `BaseEntity` 获得基础字段支持

## 注意事项

1. **线程安全**: 上下文类使用了线程安全的设计，可以在多线程环境中使用
2. **性能考虑**: 避免频繁调用Spring上下文工具类，建议在需要时才获取Bean
3. **异常处理**: 合理使用异常码，避免硬编码错误信息
4. **国际化**: 所有面向用户的消息都应该支持国际化
5. **配置管理**: 使用配置属性类管理配置，避免硬编码配置值

## 依赖关系

该模块依赖以下模块：
- `app-infra-toolkit`: 基础工具包
- `app-api-system`: 系统API接口

被以下模块依赖：
- 各业务模块 (`app-biz-*`)
- Web模块 (`app-web-*`)
- 启动模块 (`bootstrap`)

## 版本历史

- v1.0.0: 初始版本，提供基础业务功能
- 后续版本将根据业务需求持续迭代

---

*本文档最后更新时间: 2025年1月*