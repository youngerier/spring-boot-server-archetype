# White Label Server 项目文档

## 项目概述

White Label Server 是一个基于 Spring Boot 的多模块微服务项目，提供了完整的用户管理、权限控制等功能，可作为各类业务系统的基础框架。

## 项目结构

项目采用分层架构设计，各模块职责清晰：

```
white-label-server/
├── app-api/                # API接口定义层
│   ├── app-api-rbac/       # 权限管理API
│   └── app-api-user/       # 用户管理API
├── app-biz/                # 业务逻辑实现层
│   ├── app-biz-business-code/     # 业务状态码
│   ├── app-biz-rbac/       # 权限管理业务逻辑
│   └── app-biz-user/       # 用户管理业务逻辑
├── app-core/               # 核心模块，包含公共组件
├── app-infra/              # 基础设施层
│   ├── app-infra-business/ # 业务基础设施
│   └── app-infra-toolkit/  # 工具类库
├── app-web/                # Web层，处理HTTP请求
│   ├── app-web-admin/      # 管理后台接口
│   ├── app-web-common/     # Web层公共组件
│   └── app-web-member/     # 会员端接口
├── bootstrap/              # 应用启动模块
│   ├── app-admin/          # 管理后台启动模块
│   └── app-member/         # 会员端启动模块
├── middleware/             # 中间件组件
│   └── spring-boot-starter-jwt-auth/ # JWT认证中间件
├── doc/                    # 项目文档
├── k8s/                    # Kubernetes配置
└── tests/                  # 测试模块
```

## 技术栈

### 核心框架

- **Java版本**: JDK 17
- **构建工具**: Maven
- **核心框架**: Spring Boot 3.3.3
- **微服务**: Spring Cloud 2023.0.1

### 数据库相关

- **ORM框架**: MyBatis 3.5.15
- **增强ORM**: MyBatis-Flex 1.11.1
- **数据库**: MySQL 8.0.29 / PostgreSQL 42.6.1

### 服务通信

- **RPC框架**: Dubbo 3.3.0
- **服务发现**: Nacos 2023.0.3.2

### 工具库

- **对象映射**: MapStruct 1.6.3
- **代码简化**: Lombok
- **HTTP客户端**: OkHttp 4.12.0
- **JSON处理**: FastJSON2 2.0.30

### API文档

- **API文档**: Knife4j 4.6.0 (基于Swagger)
- **OpenAPI**: OpenAPI 3.0 (Swagger OpenAPI v3 2.2.9)

### 安全相关

- **认证授权**: 自定义JWT认证 (spring-boot-starter-jwt-auth)
- **JWT库**: JJWT 0.12.3

### 其他工具

- **线程池管理**: DynamicTP 1.1.9.1
- **数据校验**: Jakarta Validation 3.0.2
- **测试工具**: Instancio JUnit 5.4.0
- **代码生成**: Generator Maven Plugin 1.0.1

## 开发规范

### 代码生成

项目使用自定义的Generator Maven Plugin进行代码生成，简化开发流程：

1. 在实体类上添加`@GenModel`注解
2. 配置`pom.xml`中的`generator-maven-plugin`插件
3. 执行Maven构建，自动生成相关代码

详细配置请参考：[PLUGIN_CONFIGURATION.md](./PLUGIN_CONFIGURATION.md)

### 测试规范

项目重视测试，提供了完整的测试指南：

- 单元测试
- 集成测试
- 端到端测试

详细测试规范请参考：[spring_boot_testing_guide.md](./spring_boot_testing_guide.md)

### 项目构建与部署

#### 本地开发

1. 克隆项目到本地
2. 配置Maven环境（可使用项目提供的`settings.xml`）
3. 执行`mvn clean install`构建项目
4. 启动所需的服务模块

#### Docker部署

项目提供了Docker支持：

1. 使用`build.sh`脚本构建Docker镜像
2. 查看各启动模块中的`Dockerfile`了解详细配置

#### Kubernetes部署

项目提供了Kubernetes配置：

1. 查看`k8s/service.yaml`了解服务配置
2. 根据实际环境调整配置参数

## 模块说明

### API层 (app-api)

定义服务接口和数据传输对象(DTO)，供其他模块调用。

### 业务层 (app-biz)

实现核心业务逻辑，处理业务规则和流程。

### 核心层 (app-core)

提供公共组件、常量定义、异常处理等基础功能。

### 基础设施层 (app-infra)

提供数据访问、缓存、消息队列等基础设施服务。

### Web层 (app-web)

处理HTTP请求，实现REST API接口。

### 启动模块 (bootstrap)

应用程序入口，整合各模块并提供启动配置。

### 中间件 (middleware)

自定义中间件组件，如JWT认证等。

## 快速开始

1. 确保已安装JDK 17和Maven
2. 克隆项目到本地
3. 执行`mvn clean install -DskipTests`构建项目
4. 启动所需的服务模块：
    - 管理后台: `java -jar bootstrap/app-admin/target/app-admin.jar`
    - 会员端: `java -jar bootstrap/app-member/target/app-member.jar`

## 贡献指南

1. Fork项目
2. 创建特性分支
3. 提交变更
4. 推送到分支
5. 创建Pull Request

## 许可证

[项目许可证信息]