#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.response;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @Author: jiang
 *   @Site:
 *   @Date: 2025/10/13 10:58
 * 响应对象
 */
@Data
public class TenantProfileResponse {
    private Long id;

    /**
     * 租户配置ID
     *
     */
    private Long tenantConfigId;

    /**
     * 租户ID
     *
     */
    private Long tenantId;

    /**
     * 设置键
     *
     */
    private String settingKey;

    /**
     * 设置值
     *
     */
    private String settingValue;

    /**
     * 版本号
     *
     */
    private Integer version;

    /**
     * 备注
     *
     */
    private String remarks;

    /**
     * 创建时间
     *
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     *
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     *
     */
    private LocalDateTime deleteTime;
}
