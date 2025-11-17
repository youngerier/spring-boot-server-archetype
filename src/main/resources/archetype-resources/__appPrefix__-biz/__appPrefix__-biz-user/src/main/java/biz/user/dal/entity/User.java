#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.dal.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import ${package}.api.user.model.enums.ActivationStatusEnum;
import ${package}.api.user.model.enums.UserTypeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Table("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private Long id;


    /** 登录邮箱 */
    private String email;

    private String phone;

    /**
     *  PBKDF2 加密密码
     *  */
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

    private Boolean rememberMe;

    private String language;

}