#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.provider.authorization;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

/**
 * 基础授权管理器接口
 * 应用可以实现此接口来提供自定义的授权逻辑
 */
public interface BaseAuthorizationManager extends AuthorizationManager<RequestAuthorizationContext> {

    /**
     * 检查用户是否有权限访问指定资源
     *
     * @param authentication 认证信息
     * @param request        HTTP请求
     * @param requestPath    请求路径
     * @param httpMethod     HTTP方法
     * @return 是否有权限
     */
    boolean hasPermission(Authentication authentication, HttpServletRequest request, String requestPath, String httpMethod);

    /**
     * 默认的授权检查实现
     */
    @Override
    default AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String requestPath = request.getRequestURI();
        String httpMethod = request.getMethod();

        Authentication auth = authentication.get();
        boolean hasPermission = hasPermission(auth, request, requestPath, httpMethod);

        return new AuthorizationDecision(hasPermission);
    }
}