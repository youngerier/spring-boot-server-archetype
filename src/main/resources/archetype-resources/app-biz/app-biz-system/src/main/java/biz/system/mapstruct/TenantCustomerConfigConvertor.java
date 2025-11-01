#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.mapstruct;

import ${package}.api.tenant.model.dto.TenantCustomerConfigDTO;
import ${package}.api.tenant.model.request.TenantCustomerConfigRequest;
import ${package}.api.tenant.model.response.TenantCustomerConfigResponse;
import ${package}.biz.system.dal.entity.TenantCustomerConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:43
 * 租户配置表
 * 对象转换器
 */
@Mapper
public interface TenantCustomerConfigConvertor {
    TenantCustomerConfigConvertor INSTANCE = Mappers.getMapper(TenantCustomerConfigConvertor.class);

    TenantCustomerConfigDTO toDto(TenantCustomerConfig tenantCustomerConfig);

    TenantCustomerConfigDTO toDto(TenantCustomerConfigRequest tenantCustomerConfigRequest);

    TenantCustomerConfig toEntity(TenantCustomerConfigDTO tenantCustomerConfigDTO);

    TenantCustomerConfig toEntity(TenantCustomerConfigRequest tenantCustomerConfigRequest);

    TenantCustomerConfigResponse toResponse(TenantCustomerConfig tenantCustomerConfig);

    List<TenantCustomerConfigDTO> toDtoList(List<TenantCustomerConfig> tenantCustomerConfigList);

    List<TenantCustomerConfigResponse> toResponseList(
            List<TenantCustomerConfig> tenantCustomerConfigList);
}
