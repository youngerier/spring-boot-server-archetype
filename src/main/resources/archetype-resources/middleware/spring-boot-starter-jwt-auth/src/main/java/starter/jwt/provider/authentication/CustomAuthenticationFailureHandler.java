#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.provider.authentication;

import com.alibaba.fastjson2.JSON;
import ${package}.infra.business.Result;
import ${package}.infra.toolkits.network.HttpResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint,
        AccessDeniedHandler, AuthenticationFailureHandler {

    public static void writeJson(HttpServletResponse response, Object data) {
        HttpResponseUtil.writeJsonText(response, JSON.toJSONString(data));
    }

    /**
     * 处理未认证访问
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("commence authentication error", exception);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String message = "Not logged in or login has expired";
        if (exception != null) {
            if (exception instanceof AuthenticationCredentialsNotFoundException) {
                message = "Not logged in or login has expired";
            } else if (StringUtils.isNotBlank(exception.getMessage())) {
                message = exception.getMessage();
            }
        }
        writeJson(response, Result.fail(message));
    }

    /**
     * AuthenticationFailureHandler
     * 处理认证过程中的失败
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.error("authentication error", exception);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        writeJson(response, Result.fail("处理认证过程异常"));
    }

    /**
     * AccessDeniedHandler
     * 处理已认证但是权限不足的访问
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        log.error("access denied exception", exception);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        writeJson(response, Result.fail("您没有访问该资源的权限"));
    }
}