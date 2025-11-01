# Generator Maven Plugin 配置指南

## 插件现状

你的 `ping-user` 模块已经正确配置了 `generator-maven-plugin`，插件可以正常运行，但需要在实体类上添加 `@GenModel` 注解才能生成代码。

## 当前配置

### pom.xml 配置

```xml

<plugin>
    <groupId>io.github.youngerier</groupId>
    <artifactId>generator-maven-plugin</artifactId>
    <configuration>
        <scanPackages>
            <package>com.example.biz.user.dal.entity</package>
        </scanPackages>
    </configuration>
    <executions>
        <execution>
            <id>generate-code</id>
            <phase>process-classes</phase>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 依赖配置

```xml

<dependencies>
    <dependency>
        <groupId>${groupId}</groupId>
        <artifactId>app-api-user</artifactId>
    </dependency>
</dependencies>
```

## 使用方式

### 1. 在实体类上添加 @GenModel 注解

```java
package com.example.biz.user.dal.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.github.youngerier.annotation.GenModel;  // 需要这个注解
import lombok.Data;

@Data
@GenModel  // 添加这个注解告诉插件需要为此类生成代码
@Table("t_user")
public class User implements Serializable {
    // ... 实体字段
}
```

### 2. 运行代码生成

```bash
# 方式1: 通过Maven生命周期自动触发
mvn clean compile

# 方式2: 直接执行插件
mvn process-classes -pl ping-biz/ping-user

# 方式3: 直接运行插件目标
mvn pojo-codegen:generate -pl ping-biz/ping-user

mvn clean compile process-classes -pl app-biz/app-biz-rbac -am
```

### 3. 生成的代码结构

执行成功后，代码会生成在 `target/generated-sources` 目录下：

```
ping-biz/ping-user/target/generated-sources/
├── dto/
│   ├── UserDto.java
│   └── UserCredentialDto.java
├── request/
│   ├── UserRequest.java
│   └── UserCredentialRequest.java
├── response/
│   ├── UserResponse.java
│   └── UserCredentialResponse.java
├── query/
│   ├── UserQuery.java
│   └── UserCredentialQuery.java
├── service/
│   ├── UserService.java
│   └── UserCredentialService.java
├── service/impl/
│   ├── UserServiceImpl.java
│   └── UserCredentialServiceImpl.java
├── repository/
│   ├── UserRepository.java
│   └── UserCredentialRepository.java
└── converter/
    ├── UserConverter.java
    └── UserCredentialConverter.java
```

## 插件执行状态

### 当前执行结果

```
[INFO] --- pojo-codegen:1.0.1:generate (generate-code) @ ping-user ---
[INFO] Starting POJO code generation...
[INFO] Output directory: /Users/pingwang/code/java-projects/ping-gateway/ping-biz/ping-user/target/generated-sources
[INFO] Reflections took 797 ms to scan 84 urls, producing 248 keys and 12390 values
[INFO] Found 0 classes annotated with @GenModel: []
[WARNING] No POJOs with @GenModel annotation found in specified packages. Skipping code generation.
```

**解释**: 插件已经正确运行，扫描了指定的包 `com.example.biz.user.dal.entity`，但没有找到带有 `@GenModel` 注解的类，所以跳过了代码生成。

### 解决方案

要让插件生成代码，需要：

1. **确保 codegen-core 依赖可用** （已配置）
2. **在实体类上添加 @GenModel 注解**
3. **重新编译并运行插件**

### 注意事项

1. **注解依赖**: `@GenModel` 注解来自 `io.github.youngerier:codegen-core` 依赖
2. **编译时依赖**: 将 codegen-core 设置为 `provided` 作用域，因为它只在编译时需要
3. **扫描包路径**: 确保 `scanPackages` 配置指向正确的实体类包路径
4. **执行阶段**: 插件绑定到 `process-classes` 阶段，确保在类文件编译后执行

### 常见问题

1. **找不到 @GenModel 注解**
    - 检查 codegen-core 依赖是否正确添加
    - 确保依赖版本匹配

2. **插件没有找到类**
    - 检查 scanPackages 配置是否正确
    - 确保项目已经编译（target/classes 目录存在类文件）

3. **生成的代码编译错误**
    - 检查生成代码的依赖是否完整
    - 确保注解处理器配置正确

## 测试验证

你可以通过以下方式验证插件配置：

```bash
# 1. 检查插件是否正确加载
mvn help:describe -Dplugin=io.github.youngerier:generator-maven-plugin

# 2. 检查类路径中是否有实体类
ls -la ping-biz/ping-user/target/classes/com/example/biz/user/dal/entity/

# 3. 运行插件并查看详细日志
mvn pojo-codegen:generate -pl ping-biz/ping-user -X
```

插件配置已经完成，只需要在实体类上添加 `@GenModel` 注解即可开始使用代码生成功能。