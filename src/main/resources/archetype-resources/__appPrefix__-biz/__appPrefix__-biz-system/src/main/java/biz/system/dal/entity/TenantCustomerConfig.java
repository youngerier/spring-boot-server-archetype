#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:43
 * 租户配置表
 **/
@Data
@Table("tenant_customer_config")
public class TenantCustomerConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 5992112862953205632L;

    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
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
     * 访问令牌
     */
    @Column("accessToken")
    private String accessToken;

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
