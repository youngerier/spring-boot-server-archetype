#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:58
 * 请求参数对象
 */
@Data
public class TenantProfileRequest {
    /**
     * 租户配置ID
     */
    private Long tenantConfigId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 设置键
     */
    private String settingKey;

    /**
     * 设置值
     */
    private String settingValue;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;
}
