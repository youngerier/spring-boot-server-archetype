#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * RBAC用户角色关联实体类
 * 响应对象
 */
@Data
public class RbacUserRoleResponse {
    /**
     * 主键ID
     *
     */
    private Long id;

    /**
     * 用户ID
     *
     */
    private Long userId;

    /**
     * 角色ID
     *
     */
    private Long roleId;

    /**
     * 创建时间
     *
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     *
     */
    private LocalDateTime gmtModified;

    /**
     * 创建人
     *
     */
    private String createdBy;

    /**
     * 更新人
     *
     */
    private String updatedBy;
}
