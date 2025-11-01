#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.request;

import ${package}.api.rbac.model.enums.ResourceTypeEnum;
import ${package}.api.rbac.model.enums.StatusEnum;
import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.DefaultOrderField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * RBAC资源实体类 - 增强版本，支持直接权限管理
 * 查询参数对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RbacResourceQuery extends AbstractPageQuery<DefaultOrderField> {
    private Long id;

    /**
     * 资源编码，全局唯一
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源类型：MENU-菜单，BUTTON-按钮，API-接口
     */
    private ResourceTypeEnum resourceType;

    /**
     * 资源URL或路径 
     *        GET user/create
     *        POST user/update
     */
    private String resourceUrl;

    /**
     * 操作类型，如：READ, WRITE, DELETE, EXECUTE等
     *       可以用逗号分隔多个操作，如："READ,WRITE"
     */
    private String operation;

    /**
     * 父资源ID，用于构建树形结构
     */
    private Long parentId;

    /**
     * 资源描述
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
     * 扩展属性（JSON格式，存储额外的配置信息）
     */
    private String metadata;

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
