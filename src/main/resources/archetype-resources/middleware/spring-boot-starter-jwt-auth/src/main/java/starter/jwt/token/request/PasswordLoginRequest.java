#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.token.request;

import ${package}.infra.business.AppType;
import ${package}.infra.business.AssertUtils;
import ${package}.infra.business.PrincipalType;
import ${package}.starter.jwt.token.LoginMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 密码登录请求
 * <pre>
 * {
 *     "principal": "admin",
 *     "credentials": "123456",
 *     "principalType": "USERNAME" /"EMAIL"
 * }
 * </pre>
 * @author wp
 */
@Getter
public class PasswordLoginRequest extends AbstractLoginRequest {

    static final String PARAM_NAME_PRINCIPAL = "username";

    static final String PARAM_NAME_PASSWORD = "password";

    static final String PARAM_NAME_PRINCIPAL_TYPE = "principalType";

    static final String PARAM_NAME_CAPTCHA = "captcha";

    static final String PARAM_NAME_REMEMBER_ME = "rememberMe";

    @Schema(description = "验证码, 可空")
    private final String captcha;

    @Schema(description = "是否记住我, 可空", defaultValue = "false")
    private final Boolean rememberMe;

    @Schema(description = "账号主体类型", hidden = true)
    private final PrincipalType principalType;

    public PasswordLoginRequest(AppType appType, String principal, String credentials, String principalType, String captcha, String rememberMe) {
        super(appType, principal, credentials);
        this.principalType = StringUtils.hasText(principalType) ? PrincipalType.valueOf(principalType) : PrincipalType.EMAIL;
        this.captcha = captcha;
        this.rememberMe = Boolean.parseBoolean(rememberMe);
    }


    public static PasswordLoginRequest of(AppType appType, Map<String, String> params) {
        String username = params.get(PARAM_NAME_PRINCIPAL);
        String password = params.get(PARAM_NAME_PASSWORD);
        String captcha = params.get(PARAM_NAME_CAPTCHA);
        String rememberMe = params.getOrDefault(PARAM_NAME_REMEMBER_ME, "false");

        AssertUtils.hasLength(username, String.format("argument %s must not empty", PARAM_NAME_PRINCIPAL));
        AssertUtils.hasLength(password, String.format("argument %s must not empty", PARAM_NAME_PASSWORD));
        return new PasswordLoginRequest(appType, username, password,
                params.get(PARAM_NAME_PRINCIPAL_TYPE),
                captcha,
                rememberMe
        );
    }

    @Override
    public LoginMethod getLoginMethod() {
        return LoginMethod.PASSWORD;
    }
}