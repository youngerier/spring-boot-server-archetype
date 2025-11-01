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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import ${package}.web.common.filter.order.WebFilterOrdered;

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

        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        try {
            filterChain.doFilter(requestWrapper, response);
        } finally {
            long elapsedMs = System.currentTimeMillis() - startTime;

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
        if (names != null) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                sb.append(name).append(": ");
                Enumeration<String> values = request.getHeaders(name);
                if (values != null) {
                    boolean first = true;
                    while (values.hasMoreElements()) {
                        if (!first) {
                            sb.append(", ");
                        }
                        sb.append(values.nextElement());
                        first = false;
                    }
                }
                sb.append("${symbol_escape}n");
            }
        }
        return sb.toString();
    }

    private String formatResponseHeaders(HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        java.util.Collection<String> names = response.getHeaderNames();
        if (names != null) {
            for (String name : names) {
                sb.append(name).append(": ");
                java.util.Collection<String> values = response.getHeaders(name);
                if (values != null && !values.isEmpty()) {
                    boolean first = true;
                    for (String v : values) {
                        if (!first) {
                            sb.append(", ");
                        }
                        sb.append(v);
                        first = false;
                    }
                }
                sb.append("${symbol_escape}n");
            }
        }
        return sb.toString();
    }

    @Override
    public int getOrder() {
        return WebFilterOrdered.TraceFilter.getOrder() + 40;
    }
}