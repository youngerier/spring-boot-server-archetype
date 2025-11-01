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
 * @Date: 2025/10/13 14:49
 * 租户品牌配置
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantBrandConfigDTO {

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
      * 品牌介绍
      */
    private String brandColor;

     /**
      * 品牌描述
      */
    private String brandDesc;
}
