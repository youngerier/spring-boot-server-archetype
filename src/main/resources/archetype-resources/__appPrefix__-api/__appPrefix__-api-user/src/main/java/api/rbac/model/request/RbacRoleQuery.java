#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.request;

import ${package}.api.rbac.model.enums.RoleTypeEnum;
import ${package}.api.rbac.model.enums.StatusEnum;
import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.DefaultOrderField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * RBAC角色实体类
 * 查询参数对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacRoleQuery extends AbstractPageQuery<DefaultOrderField> {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色编码，全局唯一
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型：SYSTEM-系统角色，BUSINESS-业务角色，CUSTOM-自定义角色
     */
    private RoleTypeEnum roleType;

    /**
     * 父角色ID，支持角色层级结构
     */
    private Long parentId;

    /**
     * 角色级别，数字越小级别越高
     */
    private Integer roleLevel;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态：DISABLED-禁用，ENABLED-启用
     */
    private StatusEnum status;

    /**
     * 排序
     */
    private Integer sortOrder;

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
