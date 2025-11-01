-- ================================
-- 用户模块表结构
-- ================================

-- 用户基础信息表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`                   BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `gmt_create`           TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `u_id`                 VARCHAR(64) NOT NULL COMMENT '用户站内 u_id',
    `nickname`             VARCHAR(100) COMMENT '用户昵称',
    `user_name`            VARCHAR(100) COMMENT '用户名',
    `gender`               SMALLINT  DEFAULT 0 COMMENT '性别: 0 - 未知（保密），1 - 男，2 - 女',
    `country_calling_code` VARCHAR(10) COMMENT '手机号码区号',
    `mobile_phone`         VARCHAR(20) COMMENT '手机号码',
    `email`                VARCHAR(100) COMMENT '邮箱',
    `avatar`               VARCHAR(500) COMMENT '头像',
    `wechat`               VARCHAR(100) COMMENT '微信号',
    `tenant_id`            BIGINT    DEFAULT 0 COMMENT '归属租户: 运营用户为 0',
    `is_enabled`           SMALLINT  DEFAULT 1 COMMENT '是否启用: 0 - 禁用，1 - 启用',
    `is_archive`           SMALLINT  DEFAULT 0 COMMENT '是否已注销: 0:未注销，1: 已注销',
    `archive_version`      INT       DEFAULT 0 COMMENT '注销版本号',
    `frozen_expired_date`  TIMESTAMP COMMENT '冻结到期时间',
    `frozen_reason`        VARCHAR(500) COMMENT '冻结原因',
    `remark`               VARCHAR(500) COMMENT '用户介绍、备注',
    PRIMARY KEY (`id`)
) COMMENT ='用户基础信息表';

-- 创建索引
CREATE UNIQUE INDEX `uk_u_id` ON `t_user` (`u_id`);
CREATE UNIQUE INDEX `uk_user_name` ON `t_user` (`user_name`);
CREATE UNIQUE INDEX `uk_mobile_phone` ON `t_user` (`mobile_phone`);
CREATE UNIQUE INDEX `uk_email` ON `t_user` (`email`);
CREATE INDEX `idx_tenant_id` ON `t_user` (`tenant_id`);
CREATE INDEX `idx_is_enabled` ON `t_user` (`is_enabled`);
CREATE INDEX `idx_is_archive` ON `t_user` (`is_archive`);
CREATE INDEX `idx_create_time` ON `t_user` (`gmt_create`);

-- 用户凭证表
DROP TABLE IF EXISTS `t_user_credential`;
CREATE TABLE IF NOT EXISTS `t_user_credential`
(
    `id`                    BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `gmt_create`            TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`          TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `user_id`               BIGINT       NOT NULL COMMENT '关联用户ID',
    `u_id`                  VARCHAR(64)  NOT NULL COMMENT '用户站内 u_id',
    `credential_type`       VARCHAR(20)  NOT NULL COMMENT '凭证类型: password - 密码, mobile - 手机号',
    `credential_identifier` VARCHAR(100) NOT NULL COMMENT '凭证标识符: 手机号/用户名等',
    `credential_value`      VARCHAR(500) NOT NULL COMMENT '凭证值: 密码hash等',
    `failure_count`         INT       DEFAULT 0 COMMENT '失败次数: 登录失败累计次数',
    `last_failure_time`     TIMESTAMP COMMENT '最后失败时间',
    `credential_expired_date` TIMESTAMP COMMENT '凭证过期时间',
    `is_enabled`            SMALLINT  DEFAULT 1 COMMENT '是否启用: 0 - 禁用，1 - 启用',
    `tenant_id`             BIGINT    DEFAULT 0 COMMENT '归属租户: 运营用户为 0',
    PRIMARY KEY (`id`)
) COMMENT ='用户凭证表';

-- 创建索引
CREATE UNIQUE INDEX `uk_credential_type_identifier` ON `t_user_credential` (`credential_type`, `credential_identifier`);
CREATE INDEX `idx_user_id` ON `t_user_credential` (`user_id`);
CREATE INDEX `idx_u_id` ON `t_user_credential` (`u_id`);
CREATE INDEX `idx_credential_type` ON `t_user_credential` (`credential_type`);
CREATE INDEX `idx_user_credential_tenant_id` ON `t_user_credential` (`tenant_id`);
CREATE INDEX `idx_user_credential_is_enabled` ON `t_user_credential` (`is_enabled`);

-- ================================
-- RBAC模块表结构
-- ================================

-- RBAC资源表
DROP TABLE IF EXISTS `t_rbac_resource`;
CREATE TABLE IF NOT EXISTS `t_rbac_resource`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `resource_code` VARCHAR(100) NOT NULL COMMENT '资源编码，全局唯一',
    `resource_name` VARCHAR(100) NOT NULL COMMENT '资源名称',
    `resource_type` VARCHAR(20)  NOT NULL COMMENT '资源类型：MENU-菜单，BUTTON-按钮，API-接口',
    `resource_url`  VARCHAR(200) COMMENT '资源URL或路径',
    `operation`     VARCHAR(100) COMMENT '操作类型，如：READ,WRITE,DELETE,EXECUTE等',
    `parent_id`     BIGINT COMMENT '父资源ID，用于构建树形结构',
    `description`   VARCHAR(500) COMMENT '资源描述',
    `status`        SMALLINT     NOT NULL COMMENT '状态：0-禁用，1-启用',
    `sort_order`    INT          NOT NULL DEFAULT 0 COMMENT '排序',
    `metadata`      TEXT COMMENT '扩展属性（JSON格式，存储额外的配置信息）',
    `gmt_create`    TIMESTAMP             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `created_by`    VARCHAR(100) COMMENT '创建人',
    `updated_by`    VARCHAR(100) COMMENT '更新人',
    PRIMARY KEY (`id`)
) COMMENT ='RBAC资源表';

-- 创建索引
CREATE UNIQUE INDEX `uk_resource_code` ON `t_rbac_resource` (`resource_code`);
CREATE INDEX `idx_resource_type` ON `t_rbac_resource` (`resource_type`);
CREATE INDEX `idx_parent_id` ON `t_rbac_resource` (`parent_id`);
CREATE INDEX `idx_status` ON `t_rbac_resource` (`status`);

-- RBAC角色表
DROP TABLE IF EXISTS `t_rbac_role`;
CREATE TABLE IF NOT EXISTS `t_rbac_role`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_code`   VARCHAR(100) NOT NULL COMMENT '角色编码，全局唯一',
    `role_name`   VARCHAR(100) NOT NULL COMMENT '角色名称',
    `role_type`   VARCHAR(20)  NOT NULL COMMENT '角色类型：SYSTEM-系统角色，BUSINESS-业务角色，CUSTOM-自定义角色',
    `parent_id`   BIGINT COMMENT '父角色ID，支持角色层级结构',
    `role_level`  INT COMMENT '角色级别，数字越小级别越高',
    `description` VARCHAR(500) COMMENT '角色描述',
    `status`      SMALLINT     NOT NULL COMMENT '状态：0-禁用，1-启用',
    `sort_order`  INT COMMENT '排序',
    `gmt_create`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `created_by`  VARCHAR(100) COMMENT '创建人',
    `updated_by`  VARCHAR(100) COMMENT '更新人',
    PRIMARY KEY (`id`)
) COMMENT ='RBAC角色表';

-- 创建索引
CREATE UNIQUE INDEX `uk_role_code` ON `t_rbac_role` (`role_code`);
CREATE INDEX `idx_role_type` ON `t_rbac_role` (`role_type`);
CREATE INDEX `idx_parent_id` ON `t_rbac_role` (`parent_id`);
CREATE INDEX `idx_status` ON `t_rbac_role` (`status`);

-- RBAC角色资源关联表
DROP TABLE IF EXISTS `t_rbac_role_resource`;
CREATE TABLE IF NOT EXISTS `t_rbac_role_resource`
(
    `id`                 BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`            BIGINT      NOT NULL COMMENT '角色ID',
    `resource_id`        BIGINT      NOT NULL COMMENT '资源ID',
    `grant_type`         VARCHAR(20) NOT NULL COMMENT '授权类型：GRANT-授权，DENY-拒绝',
    `limited_operations` VARCHAR(200) COMMENT '限制的操作类型，如果为空则继承资源的所有操作',
    `effective_time`     TIMESTAMP COMMENT '生效时间（可选，用于临时授权）',
    `expire_time`        TIMESTAMP COMMENT '失效时间（可选，用于临时授权）',
    `gmt_create`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `created_by`         VARCHAR(100) COMMENT '创建人',
    `updated_by`         VARCHAR(100) COMMENT '更新人',
    PRIMARY KEY (`id`)
) COMMENT ='RBAC角色资源关联表';

-- 创建索引
CREATE UNIQUE INDEX `uk_role_resource` ON `t_rbac_role_resource` (`role_id`, `resource_id`);
CREATE INDEX `idx_grant_type` ON `t_rbac_role_resource` (`grant_type`);
CREATE INDEX `idx_expire_time` ON `t_rbac_role_resource` (`expire_time`);

-- RBAC用户角色关联表
DROP TABLE IF EXISTS `t_rbac_user_role`;
CREATE TABLE IF NOT EXISTS `t_rbac_user_role`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`      BIGINT NOT NULL COMMENT '用户ID',
    `role_id`      BIGINT NOT NULL COMMENT '角色ID',
    `gmt_create`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `created_by`   VARCHAR(100) COMMENT '创建人',
    `updated_by`   VARCHAR(100) COMMENT '更新人',
    PRIMARY KEY (`id`)
) COMMENT ='RBAC用户角色关联表';

-- 创建索引
CREATE UNIQUE INDEX `uk_user_role` ON `t_rbac_user_role` (`user_id`, `role_id`);
CREATE INDEX `idx_user_id` ON `t_rbac_user_role` (`user_id`);
CREATE INDEX `idx_role_id` ON `t_rbac_user_role` (`role_id`);