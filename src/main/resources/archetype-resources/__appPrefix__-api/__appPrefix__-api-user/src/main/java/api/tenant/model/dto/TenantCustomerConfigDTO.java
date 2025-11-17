#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.dto;

import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @Author: jiang
 *   @Site:
 *   @Date: 2025/10/13 10:43
 *   租户配置表
 * 数据传输对象(DTO)
 */
@Data
public class TenantCustomerConfigDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 租户ID
     * 主键ID
     */
    private Long tenantId;

    /**
     * 外部账号ID
     * 主键ID
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
     * 主键ID
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
}
