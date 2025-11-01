#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:43
 * 租户配置表
 * 请求参数对象
 */
@Data
public class TenantCustomerConfigRequest {
    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 外部账号ID
     */
    private String outAccountId;

    /**
     * 域名
     */
    private String domain;

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
     * 删除时间
     */
    private LocalDateTime deleteTime;
}
