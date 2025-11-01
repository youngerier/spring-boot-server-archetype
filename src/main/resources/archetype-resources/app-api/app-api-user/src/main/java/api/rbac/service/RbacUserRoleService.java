#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.service;

import ${package}.api.rbac.model.dto.RbacUserRoleDTO;
import ${package}.api.rbac.model.request.RbacUserRoleQuery;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * RBAC用户角色关联实体类
 * 服务接口
 */
public interface RbacUserRoleService {
    /**
     * 创建RbacUserRole
     * @param rbacUserRoleDTO RbacUserRole数据传输对象
     * @return 创建的RbacUserRole对象
     */
    RbacUserRoleDTO createRbacUserRole(RbacUserRoleDTO rbacUserRoleDTO);

    /**
     * 根据ID查询RbacUserRole
     * @param id 主键ID
     * @return 对应的RbacUserRole对象
     */
    RbacUserRoleDTO getRbacUserRoleById(long id);

    /**
     * 查询所有RbacUserRole
     * @return RbacUserRole对象列表
     */
    List<RbacUserRoleDTO> queryRbacUserRoles(RbacUserRoleQuery query);

    /**
     * 分页查询RbacUserRole
     * @return RbacUserRole对象列表
     */
    Pagination<RbacUserRoleDTO> pageQueryRbacUserRoles(RbacUserRoleQuery query);

    /**
     * 更新RbacUserRole
     * @param id 主键ID
     * @param rbacUserRoleDTO RbacUserRole数据传输对象
     * @return 更新后的RbacUserRole对象
     */
    RbacUserRoleDTO updateRbacUserRole(long id, RbacUserRoleDTO rbacUserRoleDTO);

    /**
     * 删除RbacUserRole
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteRbacUserRole(long id);
}
