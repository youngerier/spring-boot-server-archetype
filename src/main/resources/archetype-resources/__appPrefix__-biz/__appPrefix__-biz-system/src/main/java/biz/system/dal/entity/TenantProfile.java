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
 * @Date: 2025/10/13 10:58
 **/
@Data
@Table("tenant_profile")
public class TenantProfile implements Serializable {
    @Serial
    private static final long serialVersionUID = 5992112862953205632L;

    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private Long id;

    /**
     * 租户配置ID
     */
    @Column(ignore = true)
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
}
