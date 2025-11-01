#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.token.request;

import ${package}.infra.business.AppType;
import ${package}.starter.jwt.token.LoginMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public abstract class AbstractLoginRequest {

    /**
     * 登录的应用端(身份)
     */
    @Schema(description = "登录的应用端(身份)", hidden = true)
    @NotNull
    private final AppType appType;

    /**
     * 租户 ID
     */
    @Schema(description = "租户", hidden = true)
    private final Long tenantId;

    /**
     * 用户身份标识
     * 可以是户名、手机号码、邮箱等
     */
    @Schema(description = "用户名、手机号码、邮箱、token")
    @NotBlank
    private final String username;

    @Schema(description = "登录凭证，密码、验证码登")
    @NotBlank
    private final String password;


    protected AbstractLoginRequest(AppType appType, String username, String credentials) {
        this.appType = appType;
        this.tenantId = 1L;
        this.username = username;
        this.password = credentials;
    }

    public abstract LoginMethod getLoginMethod();

}