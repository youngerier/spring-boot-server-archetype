#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.request;

import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.DefaultOrderField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * RBAC用户角色关联实体类
 * 查询参数对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacUserRoleQuery extends AbstractPageQuery<DefaultOrderField> {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

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
