#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.service;

import ${package}.api.rbac.model.dto.RbacResourceDTO;
import ${package}.api.rbac.model.dto.RbacResourceTreeDTO;
import ${package}.api.rbac.model.dto.RbacRoleDTO;

import java.util.List;

public interface RbacUserPermissionService {
    /**
     * 获取用户的角色列表
     * @param userId 用户ID
     * @return 用户的角色列表
     */
    List<RbacRoleDTO> getUserRoles(Long userId);

    /**
     * 获取用户的资源权限列表
     * @param userId 用户ID
     * @return 用户的资源权限列表
     */
    List<RbacResourceDTO> getUserResources(Long userId);

    /**
     * 获取用户的资源权限树形结构
     * @param userId 用户ID
     * @return 用户的资源权限树形结构
     */
    List<RbacResourceTreeDTO> getUserResourceTree(Long userId);

    /**
     * 检查用户是否具有指定角色
     * @param userId 用户ID
     * @param roleCode 角色编码
     * @return 是否具有角色
     */
    boolean hasRole(Long userId, String roleCode);

    /**
     * 检查用户是否有权访问指定资源
     * @param userId 用户ID
     * @param resourcePath 资源路径
     * @param httpMethod HTTP方法
     * @return 是否有权访问
     */
    boolean hasResourceAccess(Long userId, String resourcePath, String httpMethod);
}