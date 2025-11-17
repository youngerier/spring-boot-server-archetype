#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.service;

import ${package}.api.rbac.model.dto.RbacResourceDTO;
import ${package}.api.rbac.model.request.RbacResourceQuery;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * RBAC资源实体类 - 增强版本，支持直接权限管理
 * 服务接口
 */
public interface RbacResourceService {
    /**
     * 创建RbacResource
     * @param rbacResourceDTO RbacResource数据传输对象
     * @return 创建的RbacResource对象
     */
    RbacResourceDTO createRbacResource(RbacResourceDTO rbacResourceDTO);

    /**
     * 根据ID查询RbacResource
     * @param id 主键ID
     * @return 对应的RbacResource对象
     */
    RbacResourceDTO getRbacResourceById(long id);

    /**
     * 查询所有RbacResource
     * @return RbacResource对象列表
     */
    List<RbacResourceDTO> queryRbacResources(RbacResourceQuery query);

    /**
     * 分页查询RbacResource
     * @return RbacResource对象列表
     */
    Pagination<RbacResourceDTO> pageQueryRbacResources(RbacResourceQuery query);

    /**
     * 更新RbacResource
     * @param id 主键ID
     * @param rbacResourceDTO RbacResource数据传输对象
     * @return 更新后的RbacResource对象
     */
    RbacResourceDTO updateRbacResource(long id, RbacResourceDTO rbacResourceDTO);

    /**
     * 删除RbacResource
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteRbacResource(long id);

    /**
     * 根据ID列表批量查询RbacResource
     * @param ids ID列表
     * @return RbacResource对象列表
     */
    List<RbacResourceDTO> getRbacResourceByIds(List<Long> ids);
}