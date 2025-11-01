#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;


@Slf4j
public class LogMethodInterceptor implements MethodInterceptor {
    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        long startTime = System.currentTimeMillis();

        try {
            log.info("开始执行: {}.{}", className, methodName);
            Object result = invocation.proceed();
            long duration = System.currentTimeMillis() - startTime;
            log.info("执行完成: {}.{}, 耗时: {}ms", className, methodName, duration);
            return result;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("执行异常: {}.{}, 耗时: {}ms, 异常: {}",
                    className, methodName, duration, e.getMessage());
            throw e;
        }
    }
}
