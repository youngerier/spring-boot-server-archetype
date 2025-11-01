#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.tenant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 14:48
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantBaseConfigDTO {

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
