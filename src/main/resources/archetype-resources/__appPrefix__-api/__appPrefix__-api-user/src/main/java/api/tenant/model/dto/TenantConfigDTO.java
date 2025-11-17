#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 14:55
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantConfigDTO {

    private Long tenantId;

    /**
     * 租户基础配置
     */
    private TenantBaseConfigDTO baseConfigDTO;

    /**
     * 租户品牌配置
     */
    private TenantBrandConfigDTO brandConfigDTO;
}
