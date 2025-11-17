#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.request;

import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.DefaultOrderField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: jiang
 *   @Site:
 *   @Date: 2025/10/13 10:58
 * 查询参数对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantProfileQuery extends AbstractPageQuery<DefaultOrderField> {
    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 最小创建时间
     */
    private LocalDateTime minCreateTime;

    /**
     * 最大创建时间
     */
    private LocalDateTime maxCreateTime;

    /**
     * 最小修改时间
     */
    private LocalDateTime minUpdateTime;

    /**
     * 最大修改时间
     */
    private LocalDateTime maxUpdateTime;
}
