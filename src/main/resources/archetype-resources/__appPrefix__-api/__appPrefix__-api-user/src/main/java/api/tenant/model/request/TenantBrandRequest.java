#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 16:18
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TenantBrandRequest {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌logo
     */
    private String brandLogo;
    /**
     * 品牌颜色
     */
    private String brandColor;
    /**
     * 品牌描述
     */
    private String brandDesc;
}
