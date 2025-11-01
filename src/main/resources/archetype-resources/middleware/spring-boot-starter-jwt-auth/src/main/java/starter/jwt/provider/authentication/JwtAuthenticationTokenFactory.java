#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.provider.authentication;

import ${package}.infra.business.AppType;
import ${package}.starter.jwt.token.JwtAuthenticationToken;
import ${package}.starter.jwt.token.LoginMethod;
import ${package}.starter.jwt.token.request.PasswordLoginRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Map;
import java.util.Objects;

/**
 * JWT认证令牌工厂类
 * 负责根据不同的登录方式创建对应的认证令牌
 */
public class JwtAuthenticationTokenFactory {

    /**
     * 根据登录方式构建JWT认证令牌
     *
     * @param loginMethod   登录方式
     * @param appType       应用类型
     * @param requestParams 请求参数
     * @return JWT认证令牌
     */
    public JwtAuthenticationToken build(LoginMethod loginMethod, AppType appType, Map<String, String> requestParams) {
        if (Objects.equals(loginMethod, LoginMethod.PASSWORD)) {
            PasswordLoginRequest request = PasswordLoginRequest.of(appType, requestParams);
            return JwtAuthenticationToken.unauthenticated(request, request.getPassword());
        }
        // TODO: 实现其他登录方式的处理逻辑
        // if (Objects.equals(loginMethod, LoginMethod.MOBILE_PHONE)) { ... }
        // if (Objects.equals(loginMethod, LoginMethod.EMAIL)) { ... }

        throw new AuthenticationCredentialsNotFoundException("unknown login method: " + loginMethod);
    }
}