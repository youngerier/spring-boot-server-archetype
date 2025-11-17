#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.dto;

import ${package}.api.rbac.model.enums.GrantTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * RBAC角色资源关联实体类 - 新的简化权限管理方式
 *   直接关联角色和资源，去除中间的权限层
 * 数据传输对象(DTO)
 */
@Data
public class RbacRoleResourceDTO {
    /**
     * 主键ID
     * 主键ID
     */
    private Long id;

    /**
     * 角色ID
     * 主键ID
     */
    private Long roleId;

    /**
     * 资源ID
     * 主键ID
     */
    private Long resourceId;

    /**
     * 授权类型：GRANT-授权，DENY-拒绝（用于实现拒绝优先策略）
     */
    private GrantTypeEnum grantType;

    /**
     * 限制的操作类型，如果为空则继承资源的所有操作
     *       可以用逗号分隔多个操作，如："READ,WRITE"
     */
    private String limitedOperations;

    /**
     * 生效时间（可选，用于临时授权）
     */
    private LocalDateTime effectiveTime;

    /**
     * 失效时间（可选，用于临时授权）
     */
    private LocalDateTime expireTime;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;
}
