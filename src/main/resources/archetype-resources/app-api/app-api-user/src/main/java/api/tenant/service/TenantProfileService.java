#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.service;

import ${package}.api.tenant.model.dto.TenantProfileDTO;
import ${package}.api.tenant.model.request.TenantBrandRequest;
import ${package}.api.tenant.model.request.TenantProfileQuery;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:58
 * 服务接口
 */
public interface TenantProfileService {
    /**
     * 创建TenantProfile
     *
     * @param tenantProfileDTO TenantProfile数据传输对象
     * @return 创建的TenantProfile对象
     */
    TenantProfileDTO createTenantProfile(TenantProfileDTO tenantProfileDTO);

    /**
     * 根据ID查询TenantProfile
     *
     * @param id 主键ID
     * @return 对应的TenantProfile对象
     */
    TenantProfileDTO getTenantProfileById(long id);

    /**
     * 查询所有TenantProfile
     *
     * @return TenantProfile对象列表
     */
    List<TenantProfileDTO> queryTenantProfiles(TenantProfileQuery query);

    /**
     * 分页查询TenantProfile
     *
     * @return TenantProfile对象列表
     */
    Pagination<TenantProfileDTO> pageQueryTenantProfiles(TenantProfileQuery query);

    /**
     * 更新TenantProfile
     *
     * @param id               主键ID
     * @param tenantProfileDTO TenantProfile数据传输对象
     * @return 更新后的TenantProfile对象
     */
    TenantProfileDTO updateTenantProfile(long id, TenantProfileDTO tenantProfileDTO);

    /**
     * 更新TenantProfile
     *
     * @param tenantProfileDTO TenantProfile数据传输对象
     * @return 更新后的TenantProfile对象
     */
    TenantProfileDTO updateTenantProfile(TenantProfileDTO tenantProfileDTO);

    /**
     * 更新TenantBrand
     *
     * @param request TenantBrand请求对象
     * @return 是否更新成功
     */
    Boolean updateTenantBrand(TenantBrandRequest request);

    /**
     * 删除TenantProfile
     *
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteTenantProfile(long id);

    /**
     * 根据租户ID查询租户配置
     *
     * @param tenantId 租户ID
     * @param settingKey 配置项
     * @return 租户配置对象
     */
    TenantProfileDTO getByTenantIdAndKey(Long tenantId, String settingKey);
}
