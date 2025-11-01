#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidRequest implements Serializable {


    private Long id;

    /**
     * 创建时间
     */
    @NotNull
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 用户站内 u_id
     */
    private String uId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别: 0 - 未知（保密），1 - 男，2 - 女
     */
    private Short gender;

    /**
     * 手机号码区号
     */
    private String countryCallingCode;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 归属租户: 运营用户为 0
     */
    private Long tenantId;

    /**
     * 是否启用: 0 - 禁用，1 - 启用
     */
    private Short isEnabled;

    /**
     * 是否已注销: 0:未注销，1: 已注销
     */
    private Short isArchive;

    /**
     * 注销版本号
     */
    private Integer archiveVersion;

    /**
     * 冻结到期时间
     */
    private LocalDateTime frozenExpiredDate;

    /**
     * 冻结原因
     */
    private String frozenReason;

    /**
     * 用户身份，多个用逗号分隔
     */
    private String identity;

    /**
     * 用户 kyc 认证类型，多个用逗号隔开
     */
    private String kycType;

    /**
     * 用户介绍、备注
     */
    private String remark;

}
