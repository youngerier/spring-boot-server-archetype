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
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StopWatch;

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
public class ApiLoggingFilter extends OncePerRequestFilter implements Ordered {

    private static final int MAX_BODY_LENGTH = 4096;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        try {
            filterChain.doFilter(requestWrapper, response);
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
            long elapsedMs = stopWatch.getTotalTimeMillis();

            // 若命中的处理器上标注了 LogIgnore，则忽略日志打印
            Object handler = requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
            if (isLogIgnored(handler)) {
                return;
            }

            String method = requestWrapper.getMethod();
            String fullUrl = buildFullUrl(requestWrapper);
            String contentType = requestWrapper.getContentType();
            String headersBlock = formatHeaders(requestWrapper);
            String responseHeadersBlock = formatResponseHeaders(response);
            String body = getRequestBody(requestWrapper, contentType);

            StringBuilder sb = new StringBuilder();
            sb.append(method).append(" ").append(fullUrl);
            if (contentType != null && !contentType.isEmpty()) {
                sb.append("${symbol_escape}ncontent-type: ").append(contentType);
            }
            if (!headersBlock.isEmpty()) {
                sb.append("${symbol_escape}nheaders:${symbol_escape}n").append(headersBlock.trim());
            }
            if (!responseHeadersBlock.isEmpty()) {
                sb.append("${symbol_escape}nresponse-headers:${symbol_escape}n").append(responseHeadersBlock.trim());
            }
            if (body != null && !body.isEmpty()) {
                sb.append("${symbol_escape}n${symbol_escape}n").append(body);
            }

            log.info("{}", sb.toString());
        }
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

    private String buildFullUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String query = request.getQueryString();
        return (query == null || query.isEmpty()) ? url : (url + "?" + query);
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper, String contentType) {
        byte[] content = requestWrapper.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }
        String raw = new String(content, StandardCharsets.UTF_8);
        // 限制长度，避免日志过大
        if (raw.length() > MAX_BODY_LENGTH) {
            raw = raw.substring(0, MAX_BODY_LENGTH) + "...";
        }
        return raw;
    }

    private String formatHeaders(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> names = request.getHeaderNames();
        if (names == null) {
            return sb.toString();
        }
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Enumeration<String> values = request.getHeaders(name);
            String joined = (values == null) ? "" : String.join(", ", java.util.Collections.list(values));
            sb.append(name).append(": ").append(joined).append("${symbol_escape}n");
        }
        return sb.toString();
    }

    private String formatResponseHeaders(HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        java.util.Collection<String> names = response.getHeaderNames();
        if (names == null || names.isEmpty()) {
            return sb.toString();
        }
        for (String name : names) {
            java.util.Collection<String> values = response.getHeaders(name);
            String joined = (values == null || values.isEmpty()) ? "" : String.join(", ", values);
            sb.append(name).append(": ").append(joined).append("${symbol_escape}n");
        }
        return sb.toString();
    }

    @Override
    public int getOrder() {
        return WebFilterOrdered.TraceFilter.getOrder() + 40;
    }
}