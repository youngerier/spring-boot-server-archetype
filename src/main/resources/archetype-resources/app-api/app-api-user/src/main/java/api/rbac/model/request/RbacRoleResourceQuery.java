#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.request;

import ${package}.api.rbac.model.enums.GrantTypeEnum;
import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.DefaultOrderField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * RBAC角色资源关联实体类 - 新的简化权限管理方式
 *   直接关联角色和资源，去除中间的权限层
 * 查询参数对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacRoleResourceQuery extends AbstractPageQuery<DefaultOrderField> {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 资源ID
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

    /**
     * 最小创建时间
     */
    private LocalDateTime minGmtCreate;

    /**
     * 最大创建时间
     */
    private LocalDateTime maxGmtCreate;

    /**
     * 最小修改时间
     */
    private LocalDateTime minGmtModified;

    /**
     * 最大修改时间
     */
    private LocalDateTime maxGmtModified;
}
