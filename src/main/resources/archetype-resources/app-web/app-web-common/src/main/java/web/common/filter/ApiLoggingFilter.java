#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import ${package}.web.common.filter.order.WebFilterOrdered;
import ${package}.web.common.aop.LogIgnore;

/**
 * 简化版接口调用日志过滤器（格式化输出 + 全部请求头）
 * 示例：
 * POST http://localhost:8080/api/assets/internal/apn/send-message
 * content-type: application/json
 * headers:
 * Host: localhost:8080
 * Authorization: Bearer xxx
 * ...
 *
 * {
 *   "deviceToken": "...",
 *   "title": "title",
 *   "body": "body"
 * }
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 40)
public class ApiLoggingFilter extends CommonsRequestLoggingFilter implements Ordered {

    public ApiLoggingFilter() {
        setIncludeQueryString(true);
        setIncludeHeaders(true);
        setIncludeClientInfo(true);
        setIncludePayload(true);
        setMaxPayloadLength(4096);
        setBeforeMessagePrefix("");
        setAfterMessagePrefix("");
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true; // 统一在 afterRequest 时再决定是否跳过
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // 不打印 before，避免重复输出
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (isLogIgnored(handler)) {
            return;
        }
        log.info("{}", message);
    }

    private boolean isLogIgnored(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            // 方法级别标注忽略
            if (AnnotatedElementUtils.hasAnnotation(hm.getMethod(), LogIgnore.class)) {
                return true;
            }
            // 控制器类级别标注忽略
            Class<?> beanType = hm.getBeanType();
            return AnnotatedElementUtils.hasAnnotation(beanType, LogIgnore.class);
        }
        return false;
    }

    private boolean isLogIgnored(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            if (AnnotatedElementUtils.hasAnnotation(hm.getMethod(), ${package}.web.common.aop.LogIgnore.class)) {
                return true;
            }
            Class<?> beanType = hm.getBeanType();
            return AnnotatedElementUtils.hasAnnotation(beanType, ${package}.web.common.aop.LogIgnore.class);
        }
        return false;
    }

    @Override
    public int getOrder() {
        return WebFilterOrdered.TraceFilter.getOrder() + 40;
    }
}