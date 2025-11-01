# White Label Server 项目模块依赖关系说明

## 项目概述

White Label Server 是一个基于 Spring Boot 3.3.3 的多模块项目，采用分层架构设计，主要包含以下核心模块：

- **app-infra**: 基础设施层
- **app-core**: 核心模块
- **app-api**: API 接口定义层
- **app-biz**: 业务逻辑层
- **app-web**: Web 应用层
- **middleware**: 中间件组件
- **bootstrap**: 应用启动模块
- **tests**: 测试模块

## 模块依赖关系

### 整体依赖层次

项目的依赖层次从底层到顶层依次为：

```
app-infra (基础设施层) → app-core (核心层) → app-api (接口层) → app-biz (业务层) → app-web (Web层) → bootstrap (启动层)
```

middleware 作为独立中间件组件，可被其他模块引用。

### 主要模块依赖详情

#### 1. app-infra (基础设施层)

- **子模块**:
    - app-infra-business: 业务基础设施
    - app-infra-toolkit: 工具类库

- **依赖**:
    - 外部依赖: slf4j-api

- **被依赖**:
    - app-core 依赖 app-infra-toolkit
    - app-api 依赖 app-infra-toolkit
    - app-biz 依赖 app-infra-business

#### 2. app-core (核心层)

- **依赖**:
    - app-infra-toolkit
    - mybatis-flex-core

- **被依赖**:
    - app-biz 依赖 app-core

#### 3. app-api (接口层)

- **子模块**:
    - app-api-user: 用户相关接口
    - app-api-rbac: 权限相关接口
    - app-api-system: 系统相关接口

- **依赖**:
    - app-infra-toolkit
    - swagger-annotations
    - jakarta.validation-api
    - mybatis-flex-processor (provided)

- **被依赖**:
    - app-biz 模块可能依赖对应的 app-api 模块

#### 4. app-biz (业务层)

- **子模块**:
    - app-biz-system: 系统业务逻辑
    - app-biz-user: 用户业务逻辑
    - app-biz-rbac: 权限业务逻辑
    - app-biz-message: 消息业务逻辑
    - app-biz-notification: 通知业务逻辑

- **依赖**:
    - app-infra-business
    - app-core
    - spring-tx
    - lombok
    - slf4j-api

- **被依赖**:
    - app-web 模块依赖 app-biz 模块

#### 5. app-web (Web层)

- **子模块**:
    - app-web-admin: 管理后台 Web
    - app-web-member: 会员 Web
    - app-web-common: 通用 Web 组件

- **依赖**:
    - 对应的 app-biz 模块

- **被依赖**:
    - bootstrap 模块依赖 app-web 模块

#### 6. middleware (中间件)

- **子模块**:
    - spring-boot-starter-jwt-auth: JWT 认证中间件

- **依赖**:
    - 独立依赖，不依赖项目其他模块

- **被依赖**:
    - 可能被 app-web 或 bootstrap 模块依赖

#### 7. bootstrap (启动层)

- **子模块**:
    - app-admin: 管理后台启动模块
    - app-member: 会员端启动模块

- **依赖**:
    - app-web 模块
    - 其他所需的依赖

#### 8. tests (测试模块)

- **依赖**:
    - 项目中需要测试的模块

## 技术栈

项目使用的主要技术栈包括：

- **基础框架**: Spring Boot 3.3.3, Spring Cloud 2023.0.1
- **数据库**: MyBatis 3.5.15, MyBatis-Flex 1.11.1
- **数据库驱动**: MySQL 8.0.29, PostgreSQL 42.6.1
- **API文档**: Swagger OpenAPI 3, Knife4j 4.6.0
- **工具库**: Mapstruct 1.6.3, Lombok, FastJSON2 2.0.30
- **安全**: JJWT 0.12.3
- **其他**: OkHttp 4.12.0, Dubbo 3.3.0, Nacos 2023.0.3.2

## 构建信息

- **JDK版本**: Java 17
- **构建工具**: Maven
- **版本管理**: 使用 flatten-maven-plugin 管理版本号
- **编译优化**: 配置了编译器优化选项和注解处理器

## 模块间依赖图

```
                                 ┌─────────────┐
                                 │   tests     │
                                 └─────────────┘
                                        │
                                        ▼
┌─────────────┐               ┌─────────────────┐
│ middleware  │◄──────────────┤   bootstrap     │
└─────────────┘               └─────────────────┘
                                        │
                                        ▼
                                ┌─────────────────┐
                                │     app-web     │
                                └─────────────────┘
                                        │
                                        ▼
                                ┌─────────────────┐
                                │     app-biz     │
                                └─────────────────┘
                                        │
                      ┌────────────────┬┴───────────────┐
                      ▼                ▼                ▼
              ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
              │   app-api   │  │   app-core  │  │  app-infra  │
              └─────────────┘  └─────────────┘  └─────────────┘
                      │                │                │
                      └────────────────┼────────────────┘
                                       │
                                       ▼
                               外部依赖和库
```

## 总结

White Label Server 项目采用了清晰的分层架构设计，各模块职责明确，依赖关系合理。从底层的基础设施层到顶层的应用启动层，形成了完整的依赖链路。这种模块化设计有利于代码的维护、扩展和测试，同时也便于团队协作开发。