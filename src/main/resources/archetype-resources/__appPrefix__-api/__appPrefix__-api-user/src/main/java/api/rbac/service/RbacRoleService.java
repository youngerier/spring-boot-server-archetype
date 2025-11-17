#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.service;

import ${package}.api.rbac.model.dto.RbacRoleDTO;
import ${package}.api.rbac.model.request.RbacRoleQuery;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * RBAC角色实体类
 * 服务接口
 */
public interface RbacRoleService {
    /**
     * 创建RbacRole
     * @param rbacRoleDTO RbacRole数据传输对象
     * @return 创建的RbacRole对象
     */
    RbacRoleDTO createRbacRole(RbacRoleDTO rbacRoleDTO);

    /**
     * 根据ID查询RbacRole
     * @param id 主键ID
     * @return 对应的RbacRole对象
     */
    RbacRoleDTO getRbacRoleById(long id);

    /**
     * 查询所有RbacRole
     * @return RbacRole对象列表
     */
    List<RbacRoleDTO> queryRbacRoles(RbacRoleQuery query);

    /**
     * 分页查询RbacRole
     * @return RbacRole对象列表
     */
    Pagination<RbacRoleDTO> pageQueryRbacRoles(RbacRoleQuery query);

    /**
     * 更新RbacRole
     * @param id 主键ID
     * @param rbacRoleDTO RbacRole数据传输对象
     * @return 更新后的RbacRole对象
     */
    RbacRoleDTO updateRbacRole(long id, RbacRoleDTO rbacRoleDTO);

    /**
     * 删除RbacRole
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteRbacRole(long id);
}
