#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.rbac.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 资源同步服务
 * 自动扫描Controller中的API接口，同步到资源表
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceSyncService {

    private final WebApplicationContext webApplicationContext;

    /**
     * 异步同步API资源 - 使用 @EventListener 确保事务生效
     */
    @EventListener
    @Async
    @Transactional
    public void syncApiResourcesAsync(ApplicationReadyEvent event) {
        try {
            Thread.sleep(1000); // 等待1秒确保事务管理器完全初始化
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        syncApiResources();
    }

    /**
     * 同步API资源
     */
    public void syncApiResources() {

    }

    /**
     * 获取URL模式 - 兼容不同Spring Boot版本
     */
    private Set<String> getPatterns(RequestMappingInfo mappingInfo) {
        try {
            // 尝试使用新版本API (Spring Boot 2.6+)
            if (mappingInfo.getPathPatternsCondition() != null) {
                return mappingInfo.getPathPatternsCondition().getPatternValues();
            }
        } catch (Exception e) {
            // 忽略异常，尝试旧版本API
        }

        try {
            // 尝试使用旧版本API (Spring Boot 2.5及以下)
            if (mappingInfo.getPatternsCondition() != null) {
                return mappingInfo.getPatternsCondition().getPatterns();
            }
        } catch (Exception e) {
            log.warn("无法获取URL模式: {}", e.getMessage());
        }

        return null;
    }

    /**
     * 判断是否应该同步该资源
     */
    private boolean shouldSyncResource(String pattern, String method) {
        // 排除一些不需要权限控制的路径
        if (pattern.startsWith("/error") ||
                pattern.startsWith("/actuator") ||
                pattern.startsWith("/swagger") ||
                pattern.startsWith("/v3/api-docs") ||
                pattern.startsWith("/doc.html") ||
                pattern.startsWith("/webjars")) {
            return false;
        }

        return true;
    }


    /**
     * 生成资源编码
     */
    private String generateResourceCode(String pattern, String method) {
        // 将URL路径转换为资源编码
        String code = pattern.replaceAll("/", ":")
                .replaceAll("${symbol_escape}${symbol_escape}{[^}]+${symbol_escape}${symbol_escape}}", "id")
                .replaceFirst("^:", "");
        return method.toLowerCase() + ":" + code;
    }

    /**
     * 生成资源名称
     */
    private String generateResourceName(String pattern, String method, Method javaMethod) {
        // 尝试从注解中获取名称
        Operation operation = javaMethod.getAnnotation(Operation.class);
        if (operation != null && !operation.summary().isEmpty()) {
            return operation.summary();
        }

        // 根据方法名和URL生成名称
        String methodName = javaMethod.getName();
        return method + " " + pattern + " (" + methodName + ")";
    }

    /**
     * 生成描述
     */
    private String generateDescription(Method javaMethod, Class<?> beanType) {
        // 尝试从注解中获取描述
        Operation operation = javaMethod.getAnnotation(Operation.class);
        if (operation != null && !operation.description().isEmpty()) {
            return operation.description();
        }

        return "自动生成的API资源: " + beanType.getSimpleName() + "." + javaMethod.getName();
    }
}