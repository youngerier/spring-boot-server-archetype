#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author martinjiang
 */
@Data
public class AddAdminUserRequest {
    /**
     * 注册邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 密码
     */
    private String nickname;
    /**
     * 密码
     */
    private String phone;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
