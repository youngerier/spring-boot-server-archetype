#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author System
 *   @since 1.0.0
 * 响应对象
 */
@Data
public class UserCredentialResponse {
    /**
     * ID
     *
     */
    private Long id;

    /**
     * 创建时间
     *
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     *
     */
    private LocalDateTime gmtModified;

    /**
     * 关联用户ID
     *
     */
    private Long userId;

    /**
     * 用户站内 u_id
     *
     */
    private String uId;

    /**
     * 凭证类型: password - 密码, mobile - 手机号
     *
     */
    private String credentialType;

    /**
     * 凭证标识符: 手机号/用户名等
     *
     */
    private String credentialIdentifier;

    /**
     * 凭证值: 密码hash等
     *
     */
    private String credentialValue;

    /**
     * 失败次数: 登录失败累计次数
     *
     */
    private Integer failureCount;

    /**
     * 最后失败时间
     *
     */
    private LocalDateTime lastFailureTime;

    /**
     * 凭证过期时间
     *
     */
    private LocalDateTime credentialExpiredDate;

    /**
     * 是否启用: 0 - 禁用，1 - 启用
     *
     */
    private Short isEnabled;

    /**
     * 归属租户: 运营用户为 0
     *
     */
    private Long tenantId;
}
