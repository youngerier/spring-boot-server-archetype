#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ${package}.api.user.model.enums.ActivationStatusEnum;
import ${package}.api.user.model.enums.UserTypeEnum;
import ${package}.core.enums.KycStatus;
import ${package}.core.enums.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements LoginUser, Serializable {

    private Long id;

    /** 登录邮箱 */
    private String email;
    private String phone;
    /**
     *  PBKDF2 加密密码
     */
    @JsonIgnore
    private String passwordPbkdf2;

    /** OTP 秘钥 */
    private String otpSecret;

    /** 是否启用OTP */
    private Boolean otpEnabled;

    /** 昵称 */
    private String nickname;

    /** 姓 */
    private String lastName;

    /** 名 */
    private String firstName;

    /** 用户头像 */
    private String avatar;

    /** 性别: 0-未知,1-男,2-女 */
    private Short gender;

    /** 生日 */
    private LocalDate dob;

    /** 用户状态 */
    private ActivationStatusEnum status;

    /** 用户类型: Master / Robot */
    private UserTypeEnum type;

    /** 乐观锁版本号 */
    private Integer version;

    /** 备注 */
    private String remarks;

    /** 租户ID */
    private Long tenantId;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 修改时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除时间 */
    private LocalDateTime deleteTime;

    private LocalDateTime lastLoginTime;

    private String language;

    private Boolean rememberMe;

    private KycStatus kycStatus;

    private Long kycSubmitCount;

    private String outerAccountId;

    private LocalDateTime kycLastUpdateTime;

    private String kycRejectReason;

    private String token;

    private String display;

    @JsonIgnore
    @Override
    public String getUsername() {
        return getEmail();
    }

    @JsonIgnore
    @Override
    public Long getUserId() {
        return id;
    }

    @JsonIgnore
    @Transient
    @Override
    public String getPassword() {
        return passwordPbkdf2;
    }


    @Override
    public boolean isAdmin() {
        return Objects.nonNull(type)
                && (type == UserTypeEnum.ADMIN);
    }
}
