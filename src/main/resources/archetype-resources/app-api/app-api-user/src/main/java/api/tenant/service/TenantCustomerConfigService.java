#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.service;

import ${package}.api.tenant.model.dto.TenantConfigDTO;
import ${package}.api.tenant.model.dto.TenantCustomerConfigDTO;
import ${package}.api.tenant.model.request.TenantCustomerConfigQuery;
import ${package}.api.tenant.model.request.TenantCustomerConfigRequest;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:43
 * 租户配置表
 * 服务接口
 */
public interface TenantCustomerConfigService {
    /**
     * 创建TenantCustomerConfig
     *
     * @param tenantCustomerConfigDTO TenantCustomerConfig数据传输对象
     * @return 创建的TenantCustomerConfig对象
     */
    TenantCustomerConfigDTO createTenantCustomerConfig(
            TenantCustomerConfigDTO tenantCustomerConfigDTO);

    /**
     * 根据ID查询TenantCustomerConfig
     *
     * @param id 主键ID
     * @return 对应的TenantCustomerConfig对象
     */
    TenantCustomerConfigDTO getTenantCustomerConfigById(long id);

    /**
     * 查询所有TenantCustomerConfig
     *
     * @return TenantCustomerConfig对象列表
     */
    List<TenantCustomerConfigDTO> queryTenantCustomerConfigs(TenantCustomerConfigQuery query);

    /**
     * 分页查询TenantCustomerConfig
     *
     * @return TenantCustomerConfig对象列表
     */
    Pagination<TenantCustomerConfigDTO> pageQueryTenantCustomerConfigs(
            TenantCustomerConfigQuery query);

    /**
     * 更新TenantCustomerConfig
     *
     * @param id                      主键ID
     * @param tenantCustomerConfigDTO TenantCustomerConfig数据传输对象
     * @return 更新后的TenantCustomerConfig对象
     */
    TenantCustomerConfigDTO updateTenantCustomerConfig(long id,
                                                       TenantCustomerConfigDTO tenantCustomerConfigDTO);

    /**
     * 删除TenantCustomerConfig
     *
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteTenantCustomerConfig(long id);
    /**
     * 根据租户域名查询租户配置
     *
     * @param configRequest 租户域名
     * @return 租户配置
     */
    TenantConfigDTO queryTenantConfig(TenantCustomerConfigRequest configRequest);

    /**
     * 根据租户ID查询租户配置
     *
     * @param tenantId 租户ID
     * @return 租户配置
     */
    TenantCustomerConfigDTO getByTenantId(Long tenantId);

    /**
     * 根据租户ID查询租户配置
     *
     * @param outAccountId 租户ID
     * @return 租户配置
     */
    TenantCustomerConfigDTO getByOutAccountId(String outAccountId);
}
