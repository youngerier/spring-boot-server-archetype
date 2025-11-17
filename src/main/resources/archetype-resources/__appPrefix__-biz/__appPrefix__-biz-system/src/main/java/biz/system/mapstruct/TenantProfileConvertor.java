#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.mapstruct;

import ${package}.api.tenant.model.dto.TenantProfileDTO;
import ${package}.api.tenant.model.request.TenantProfileRequest;
import ${package}.api.tenant.model.response.TenantProfileResponse;
import ${package}.biz.system.dal.entity.TenantProfile;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:58
 * 对象转换器
 */
@Mapper
public interface TenantProfileConvertor {
    TenantProfileConvertor INSTANCE = Mappers.getMapper(TenantProfileConvertor.class);

    TenantProfileDTO toDto(TenantProfile tenantProfile);

    TenantProfileDTO toDto(TenantProfileRequest tenantProfileRequest);

    TenantProfile toEntity(TenantProfileDTO tenantProfileDTO);

    TenantProfile toEntity(TenantProfileRequest tenantProfileRequest);

    TenantProfileResponse toResponse(TenantProfile tenantProfile);

    List<TenantProfileDTO> toDtoList(List<TenantProfile> tenantProfileList);

    List<TenantProfileResponse> toResponseList(List<TenantProfile> tenantProfileList);
}
