#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "traceIdFilter", urlPatterns = "/*")
@Component
public class TraceFilter extends OncePerRequestFilter {
    private static final String TRACE_ID = "X-TraceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            // 生成或获取 Trace ID
            String traceId = getOrCreateTraceId(request);
            // 存入 MDC（绑定到当前线程）
            MDC.put(TRACE_ID, traceId);
            // 将 traceId 传递到响应头，方便调用链追踪
            response.setHeader(TRACE_ID, traceId);
            // 继续请求处理
            chain.doFilter(request, response);
        } finally {
            // 清除 MDC，防止线程池复用时污染其他请求
            MDC.clear();
        }
    }

    private String getOrCreateTraceId(HttpServletRequest request) {
        // 尝试从请求头获取 Trace ID（如微服务链路传递）
        String traceId = request.getHeader(TRACE_ID);
        return (traceId == null || traceId.isEmpty()) ? UUID.randomUUID().toString() : traceId;
    }

}

