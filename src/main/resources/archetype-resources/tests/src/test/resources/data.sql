(501, 5, 101, 'GRANT', 'READ', NULL, NULL, NOW(), NOW(), 'system', 'system');

-- 插入用户角色关联测试数据
INSERT INTO `t_rbac_user_role` (`id`, `user_id`, `role_id`, `gmt_create`, `gmt_modified`, `created_by`, `updated_by`)
VALUES
-- 管理员用户拥有系统管理员角色
(1, 1, 1, NOW(), NOW(), 'system', 'system'),
-- 张三拥有用户管理员角色
(2, 2, 2, NOW(), NOW(), 'system', 'system'),
-- 李四拥有普通用户角色
(3, 3, 5, NOW(), NOW(), 'system', 'system'),
-- 租户管理员拥有用户管理员角色
(4, 6, 2, NOW(), NOW(), 'system', 'system');

-- Auto increment will be handled automatically by H2
-- ================================
-- 用户模块测试数据
-- ================================

-- 清理现有数据
DELETE FROM `t_user_credential` WHERE id >= 0;
DELETE FROM `t_user` WHERE id >= 0;

-- 插入用户基础信息测试数据
INSERT INTO `t_user` (`id`, `u_id`, `nickname`, `user_name`, `gender`, `country_calling_code`, `mobile_phone`,
                      `email`, `avatar`, `wechat`, `tenant_id`, `is_enabled`, `is_archive`, `archive_version`,
                      `frozen_expired_date`, `frozen_reason`, `remark`, `gmt_create`, `gmt_modified`)
VALUES
-- 管理员用户
(1, 'U000001', '系统管理员', 'admin', 0, '+86', '13800138001', 'admin@example.com',
 'https://example.com/avatar/admin.jpg', 'admin_wechat', 0, 1, 0, 0,
 NULL, NULL, '系统超级管理员账户', NOW(), NOW()),

-- 普通用户
(2, 'U000002', '张三', 'zhangsan', 1, '+86', '13800138002', 'zhangsan@example.com',
 'https://example.com/avatar/zhangsan.jpg', 'zhangsan_wechat', 0, 1, 0, 0,
 NULL, NULL, '普通会员用户', NOW(), NOW()),

(3, 'U000003', '李四', 'lisi', 2, '+86', '13800138003', 'lisi@example.com',
 'https://example.com/avatar/lisi.jpg', 'lisi_wechat', 0, 1, 0, 0,
 NULL, NULL, 'VIP会员用户', NOW(), NOW()),

-- 被冻结的用户
(4, 'U000004', '王五', 'wangwu', 1, '+86', '13800138004', 'wangwu@example.com',
 'https://example.com/avatar/wangwu.jpg', 'wangwu_wechat', 0, 0, 0, 0,
 DATE_ADD(NOW(), INTERVAL 7 DAY), '违反社区规定', '被临时冻结的用户', NOW(), NOW()),

-- 已注销的用户
(5, 'U000005', '赵六', 'zhaoliu', 1, '+86', '13800138005', 'zhaoliu@example.com',
 'https://example.com/avatar/zhaoliu.jpg', 'zhaoliu_wechat', 0, 0, 1, 1,
 NULL, NULL, '主动注销的用户', NOW(), NOW()),

-- 租户用户
(6, 'U000006', '租户管理员', 'tenant_admin', 0, '+86', '13800138006', 'tenant_admin@tenant1.com',
 'https://example.com/avatar/tenant_admin.jpg', 'tenant_admin_wechat', 1001, 1, 0, 0,
 NULL, NULL, '租户1的管理员', NOW(), NOW()),

-- 海外用户
(7, 'U000007', 'John Smith', 'johnsmith', 1, '+1', '15551234567', 'john.smith@example.com',
 'https://example.com/avatar/johnsmith.jpg', '', 0, 1, 0, 0,
 NULL, NULL, '美国用户', NOW(), NOW()),

-- 测试用户（用于单元测试）
(8, 'U000008', '测试用户', 'testuser', 0, '+86', '13800138008', 'test@example.com',
 'https://example.com/avatar/test.jpg', 'test_wechat', 0, 1, 0, 0,
 NULL, NULL, '专用于单元测试的用户', NOW(), NOW());

-- 插入用户凭证测试数据
INSERT INTO `t_user_credential` (`id`, `user_id`, `u_id`, `credential_type`, `credential_identifier`,
                                 `credential_value`,
                                 `failure_count`, `last_failure_time`, `credential_expired_date`, `is_enabled`,
                                 `tenant_id`,
                                 `gmt_create`, `gmt_modified`)
VALUES
-- 管理员密码凭证 (密码: admin123)
(1, 1, 'U000001', 'password', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIh2sKwjbZBwW7aOCgHBnRSZVS',
 0, NULL, DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 0, NOW(), NOW()),

-- 管理员手机凭证
(2, 1, 'U000001', 'mobile', '13800138001', 'VERIFIED',
 0, NULL, NULL, 1, 0, NOW(), NOW()),

-- 张三密码凭证 (密码: password123)
(3, 2, 'U000002', 'password', 'zhangsan', '$2a$10$rXJhkvQZhFukOhwrOB8v9O3f2ELNwbKnDRqy9/UfzX5P1.Hk/x7V2',
 0, NULL, DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 0, NOW(), NOW()),

-- 张三手机凭证
(4, 2, 'U000002', 'mobile', '13800138002', 'VERIFIED',
 0, NULL, NULL, 1, 0, NOW(), NOW()),

-- 李四密码凭证 (密码: lisi123456)
(5, 3, 'U000003', 'password', 'lisi', '$2a$10$8YvYkXFpY9T6LGQdL9z5muO3fEkEpQ9W1mhkBZCXKvlOQdKpR4GfO',
 0, NULL, DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 0, NOW(), NOW()),

-- 李四手机凭证
(6, 3, 'U000003', 'mobile', '13800138003', 'VERIFIED',
 0, NULL, NULL, 1, 0, NOW(), NOW()),

-- 王五密码凭证 (密码: wangwu123) - 失败次数较多
(7, 4, 'U000004', 'password', 'wangwu', '$2a$10$FZ3L5V8rHQpF8V9LH0Fz8OY3x3gRN5vMbRyOw9Kz5K6HkF8S2vV2a',
 3, DATE_ADD(NOW(), INTERVAL -90 HOUR), DATE_ADD(NOW(), INTERVAL 90 DAY), 0, 0, NOW(), NOW()),

-- 王五手机凭证 - 已禁用
(8, 4, 'U000004', 'mobile', '13800138004', 'UNVERIFIED',
 1, DATE_ADD(NOW(), INTERVAL -90 HOUR), NULL, 0, 0, NOW(), NOW()),

-- 租户管理员密码凭证 (密码: tenant123)
(9, 6, 'U000006', 'password', 'tenant_admin', '$2a$10$nQJz8QfS6LkR8KzZ9X8JnOQ8KZF5FJP9yRz2P8KF5J9QZ8KzF5J9Q',
 0, NULL, DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 1001, NOW(), NOW()),

-- 租户管理员手机凭证
(10, 6, 'U000006', 'mobile', '13800138006', 'VERIFIED',
 0, NULL, NULL, 1, 1001, NOW(), NOW()),

-- John Smith 密码凭证 (密码: john123)
(11, 7, 'U000007', 'password', 'johnsmith', '$2a$10$bQfW5F8H9Kz5L6FP9yQz8KzF5J9QZ8KzF5J9QZ8KzF5J9QZ8KzF5J',
 0, NULL, DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 0, NOW(), NOW()),

-- John Smith 手机凭证
(12, 7, 'U000007', 'mobile', '15551234567', 'VERIFIED',
 0, NULL, NULL, 1, 0, NOW(), NOW()),

-- 测试用户密码凭证 (密码: test123)
(13, 8, 'U000008', 'password', 'testuser', '$2a$10$tQf8F5H9Kz5L6FP9yQz8KzF5J9QZ8KzF5J9QZ8KzF5J9QZ8KzF5J9Q',
 0, NULL, DATE_ADD(NOW(), INTERVAL 90 DAY), 1, 0, NOW(), NOW()),

-- 测试用户手机凭证
(14, 8, 'U000008', 'mobile', '13800138008', 'VERIFIED',
 0, NULL, NULL, 1, 0, NOW(), NOW());

-- ================================
-- RBAC模块测试数据
-- ================================

-- 清理现有数据
DELETE FROM `t_rbac_user_role` WHERE id >= 0;
DELETE FROM `t_rbac_role_resource` WHERE id >= 0;
DELETE FROM `t_rbac_role` WHERE id >= 0;
DELETE FROM `t_rbac_resource` WHERE id >= 0;

-- 插入资源测试数据
INSERT INTO `t_rbac_resource` (`id`, `resource_code`, `resource_name`, `resource_type`, `resource_url`, `operation`,
                               `parent_id`, `description`, `status`, `sort_order`, `metadata`, `gmt_create`,
                               `gmt_modified`,
                               `created_by`, `updated_by`)
VALUES
-- 系统管理菜单
(1, 'SYS_MGMT', '系统管理', 'menu', '', '', NULL, '系统管理主菜单', 1, 1, NULL, NOW(), NOW(), 'system', 'system'),
(2, 'USER_MGMT', '用户管理', 'menu', '/user', '', 1, '用户管理菜单', 1, 1, NULL, NOW(), NOW(), 'system', 'system'),
(3, 'ROLE_MGMT', '角色管理', 'menu', '/role', '', 1, '角色管理菜单', 1, 2, NULL, NOW(), NOW(), 'system', 'system'),
(4, 'RESOURCE_MGMT', '资源管理', 'menu', '/resource', '', 1, '资源管理菜单', 1, 3, NULL, NOW(), NOW(), 'system',
 'system'),

-- 用户管理相关API
(101, 'USER_LIST', '用户列表', 'api', '/api/users', 'READ', 2, '获取用户列表', 1, 1, NULL, NOW(), NOW(), 'system',
 'system'),
(102, 'USER_CREATE', '创建用户', 'api', '/api/users', 'WRITE', 2, '创建新用户', 1, 2, NULL, NOW(), NOW(), 'system',
 'system'),
(103, 'USER_UPDATE', '更新用户', 'api', '/api/users/{id}', 'WRITE', 2, '更新用户信息', 1, 3, NULL, NOW(), NOW(),
 'system', 'system'),
(104, 'USER_DELETE', '删除用户', 'api', '/api/users/{id}', 'DELETE', 2, '删除用户', 1, 4, NULL, NOW(), NOW(), 'system',
 'system'),

-- 角色管理相关API
(201, 'ROLE_LIST', '角色列表', 'api', '/api/roles', 'READ', 3, '获取角色列表', 1, 1, NULL, NOW(), NOW(), 'system',
 'system'),
(202, 'ROLE_CREATE', '创建角色', 'api', '/api/roles', 'WRITE', 3, '创建新角色', 1, 2, NULL, NOW(), NOW(), 'system',
 'system'),
(203, 'ROLE_UPDATE', '更新角色', 'api', '/api/roles/{id}', 'WRITE', 3, '更新角色信息', 1, 3, NULL, NOW(), NOW(),
 'system', 'system'),
(204, 'ROLE_DELETE', '删除角色', 'api', '/api/roles/{id}', 'DELETE', 3, '删除角色', 1, 4, NULL, NOW(), NOW(), 'system',
 'system'),

-- 资源管理相关API
(301, 'RESOURCE_LIST', '资源列表', 'api', '/api/resources', 'READ', 4, '获取资源列表', 1, 1, NULL, NOW(), NOW(),
 'system', 'system'),
(302, 'RESOURCE_CREATE', '创建资源', 'api', '/api/resources', 'WRITE', 4, '创建新资源', 1, 2, NULL, NOW(), NOW(),
 'system', 'system'),
(303, 'RESOURCE_UPDATE', '更新资源', 'api', '/api/resources/{id}', 'WRITE', 4, '更新资源信息', 1, 3, NULL, NOW(), NOW(),
 'system', 'system'),
(304, 'RESOURCE_DELETE', '删除资源', 'api', '/api/resources/{id}', 'DELETE', 4, '删除资源', 1, 4, NULL, NOW(), NOW(),
 'system', 'system'),

-- 按钮级别资源
(1001, 'USER_ADD_BTN', '添加用户按钮', 'button', '', 'WRITE', 2, '用户管理页面的添加按钮', 1, 1, NULL, NOW(), NOW(),
 'system', 'system'),
(1002, 'USER_EDIT_BTN', '编辑用户按钮', 'button', '', 'WRITE', 2, '用户管理页面的编辑按钮', 1, 2, NULL, NOW(), NOW(),
 'system', 'system'),
(1003, 'USER_DEL_BTN', '删除用户按钮', 'button', '', 'DELETE', 2, '用户管理页面的删除按钮', 1, 3, NULL, NOW(), NOW(),
 'system', 'system');

-- 插入角色测试数据
INSERT INTO `t_rbac_role` (`id`, `role_code`, `role_name`, `role_type`, `parent_id`, `role_level`, `description`,
                           `status`, `sort_order`, `gmt_create`, `gmt_modified`, `created_by`, `updated_by`)
VALUES (1, 'ADMIN', '系统管理员', 'SYSTEM', NULL, 1, '拥有系统所有权限的管理员角色', 1, 1, NOW(), NOW(), 'system',
        'system'),
       (2, 'USER_MGR', '用户管理员', 'BUSINESS', NULL, 2, '负责用户管理的业务角色', 1, 1, NOW(), NOW(), 'system',
        'system'),
       (3, 'ROLE_MGR', '角色管理员', 'BUSINESS', NULL, 2, '负责角色管理的业务角色', 1, 2, NOW(), NOW(), 'system',
        'system'),
       (4, 'RESOURCE_MGR', '资源管理员', 'BUSINESS', NULL, 2, '负责资源管理的业务角色', 1, 3, NOW(), NOW(), 'system',
        'system'),
       (5, 'ORDINARY_USER', '普通用户', 'CUSTOM', NULL, 3, '普通用户角色', 1, 1, NOW(), NOW(), 'system', 'system');

-- 插入角色资源关联测试数据
INSERT INTO `t_rbac_role_resource` (`id`, `role_id`, `resource_id`, `grant_type`, `limited_operations`,
                                    `effective_time`,
                                    `expire_time`, `gmt_create`, `gmt_modified`, `created_by`, `updated_by`)
VALUES
-- 系统管理员拥有所有资源权限
(1, 1, 1, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(2, 1, 2, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(3, 1, 3, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(4, 1, 4, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(5, 1, 101, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(6, 1, 102, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(7, 1, 103, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(8, 1, 104, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(9, 1, 201, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(10, 1, 202, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(11, 1, 203, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(12, 1, 204, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(13, 1, 301, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(14, 1, 302, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(15, 1, 303, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(16, 1, 304, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(17, 1, 1001, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(18, 1, 1002, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(19, 1, 1003, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),

-- 用户管理员拥有用户管理相关权限
(101, 2, 2, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(102, 2, 101, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(103, 2, 102, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(104, 2, 103, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(105, 2, 104, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(106, 2, 1001, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(107, 2, 1002, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(108, 2, 1003, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),

-- 角色管理员拥有角色管理相关权限
(201, 3, 3, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(202, 3, 201, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(203, 3, 202, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(204, 3, 203, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(205, 3, 204, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),

-- 资源管理员拥有资源管理相关权限
(301, 4, 4, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(302, 4, 301, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(303, 4, 302, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(304, 4, 303, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),
(305, 4, 304, 'GRANT', NULL, NULL, NULL, NOW(), NOW(), 'system', 'system'),

-- 普通用户只拥有查看用户列表的权限
