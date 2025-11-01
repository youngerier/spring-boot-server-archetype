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
 * @Site:
 * @Date: 2025/10/13 10:43
 * 租户配置表
 * 查询参数对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TenantCustomerConfigQuery extends AbstractPageQuery<DefaultOrderField> {
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 外部账号ID
     */
    private String outAccountId;

    /**
     * 应用域名
     */
    private String appDomain;

    /**
     * 管理域名
     */
    private String adminDomain;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 主邮箱
     */
    private String mainEmail;

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
