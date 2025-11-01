#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.service;

import ${package}.api.rbac.model.dto.RbacRoleResourceDTO;
import ${package}.api.rbac.model.request.RbacRoleResourceQuery;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * RBAC角色资源关联实体类 - 新的简化权限管理方式
 *   直接关联角色和资源，去除中间的权限层
 * 服务接口
 */
public interface RbacRoleResourceService {
    /**
     * 创建RbacRoleResource
     * @param rbacRoleResourceDTO RbacRoleResource数据传输对象
     * @return 创建的RbacRoleResource对象
     */
    RbacRoleResourceDTO createRbacRoleResource(RbacRoleResourceDTO rbacRoleResourceDTO);

    /**
     * 根据ID查询RbacRoleResource
     * @param id 主键ID
     * @return 对应的RbacRoleResource对象
     */
    RbacRoleResourceDTO getRbacRoleResourceById(long id);

    /**
     * 查询所有RbacRoleResource
     * @return RbacRoleResource对象列表
     */
    List<RbacRoleResourceDTO> queryRbacRoleResources(RbacRoleResourceQuery query);

    /**
     * 分页查询RbacRoleResource
     * @return RbacRoleResource对象列表
     */
    Pagination<RbacRoleResourceDTO> pageQueryRbacRoleResources(RbacRoleResourceQuery query);

    /**
     * 更新RbacRoleResource
     * @param id 主键ID
     * @param rbacRoleResourceDTO RbacRoleResource数据传输对象
     * @return 更新后的RbacRoleResource对象
     */
    RbacRoleResourceDTO updateRbacRoleResource(long id, RbacRoleResourceDTO rbacRoleResourceDTO);

    /**
     * 删除RbacRoleResource
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteRbacRoleResource(long id);
}
