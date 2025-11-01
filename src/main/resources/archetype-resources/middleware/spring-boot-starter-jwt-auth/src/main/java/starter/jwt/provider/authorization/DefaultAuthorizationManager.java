#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.provider.authorization;

import ${package}.starter.jwt.properties.AuthSecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Optional;

/**
 * 默认授权管理器实现
 * 提供基本的路径匹配授权逻辑
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultAuthorizationManager implements BaseAuthorizationManager {

    private final AuthSecurityProperties properties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(Authentication authentication, HttpServletRequest request, String requestPath, String httpMethod) {
        // 检查是否为公开路径（无需认证）
        if (isPublicPath(requestPath)) {
            return true;
        }

        // 检查是否为认证相关路径（登录/token操作）
        if (isAuthPath(requestPath)) {
            return true;
        }

        // 检查用户是否已认证
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated()) {
            log.warn("Unauthenticated access attempt to: {}", requestPath);
            return false;
        }

        // 检查是否为仅需认证的路径（无需特定权限）
        if (isAuthenticatedOnlyPath(requestPath)) {
            return true;
        }

        // 默认需要具体权限验证，由子类实现
        log.debug("Path {} requires specific permission verification", requestPath);
        return true;
    }

    /**
     * 检查是否为公开路径
     */
    private boolean isPublicPath(String requestPath) {
        return Arrays.stream(properties.getPublicPaths())
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestPath));
    }

    /**
     * 检查是否为认证相关路径
     */
    private boolean isAuthPath(String requestPath) {
        return Arrays.stream(Optional.ofNullable(properties.getAuthPaths()).orElse(new String[0]))
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestPath));
    }

    /**
     * 检查是否为仅需认证的路径
     */
    private boolean isAuthenticatedOnlyPath(String requestPath) {
        return Arrays.stream(properties.getAuthenticatedOnlyPaths())
                .anyMatch(pattern -> antPathMatcher.match(pattern, requestPath));
    }
}