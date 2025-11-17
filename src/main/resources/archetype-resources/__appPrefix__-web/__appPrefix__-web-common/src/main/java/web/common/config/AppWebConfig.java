#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.config;

import org.springframework.core.convert.converter.Converter;
import ${package}.infra.business.json.SerizalizerConfig;
import ${package}.web.common.aop.Log;
import ${package}.web.common.aop.LogMethodInterceptor;
import org.dromara.dynamictp.core.spring.EnableDynamicTp;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@EnableDynamicTp
@Configuration
public class AppWebConfig implements WebMvcConfigurer {

    @Bean
    public LogMethodInterceptor logMethodInterceptor() {
        return new LogMethodInterceptor();
    }

    @Bean
    public DefaultBeanFactoryPointcutAdvisor advisor(LogMethodInterceptor advice) {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("@annotation(" + Log.class.getName() + ")");
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();
        advisor.setOrder(1);
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);

        return advisor;
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return SerizalizerConfig::customizeBuilder;
    }

    /**
     * 添加自定义转换器，支持 URL 查询参数中的时间戳转换为 LocalDateTime
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加字符串到 LocalDateTime 的转换器，支持时间戳和 ISO 8601 格式
        registry.addConverter(new StringToLocalDateTimeConverter());
    }
    /**
     * 字符串到 LocalDateTime 的转换器实现类
     */
    private static class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }

            source = source.trim();

            // 尝试解析为时间戳（毫秒）
            try {
                long timestamp = Long.parseLong(source);
                // 统一使用 UTC 时区，与 SerizalizerConfig 保持一致
                return LocalDateTime.ofEpochSecond(timestamp / 1000, (int) (timestamp % 1000 * 1_000_000), ZoneOffset.UTC);
            } catch (NumberFormatException e) {
                // 如果不是时间戳，尝试解析为 ISO 8601 格式
                try {
                    return LocalDateTime.parse(source);
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Unable to parse time format: " + source + ". Supported formats: timestamp(milliseconds) or ISO 8601 format(e.g: 2024-01-01T00:00:00)", ex);
                }
            }
        }
    }


}
