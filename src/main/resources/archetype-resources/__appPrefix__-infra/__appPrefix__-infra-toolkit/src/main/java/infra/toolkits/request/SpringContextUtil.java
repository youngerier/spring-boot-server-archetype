#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.request;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author litao
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取当前环境
     *
     * @return env
     */
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    public static boolean isProd() {
        return "prod".equals(getActiveProfile());
    }

    public static boolean isTest() {
        return "test".equals(getActiveProfile());
    }

    public static boolean isDev() {
        return "dev".equals(getActiveProfile());
    }

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 通过Annotation获取 Bean.
     *
     * @param clazz 注解类
     * @return Map<String, Object>
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return context.getBeansWithAnnotation(clazz);
    }

    /**
     * 获取注解的类名
     *
     * @param clazz 注解类
     * @return String[]
     */
    public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> clazz) {
        return context.getBeanNamesForAnnotation(clazz);
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name 名称
     * @return Object
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz 类
     * @param <T>   类
     * @return <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 通过bean名称获取bean
     *
     * @param beanName bena名称
     * @param clazz    类型
     * @param <T>      泛型
     * @return bean
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return context.getBean(beanName, clazz);
    }


    /**
     * the bean instances that match the given object type (including subclasses),
     * judging from either bean definitions or the value of getObjectType in the case of FactoryBeans.
     *
     * @param clazz – the class or interface to match, or null for all concrete beans
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }


    /**
     * 发布事件
     *
     * @param event event object {@link ApplicationEvent}
     */
    public static void publishEvent(ApplicationEvent event) {
        if (context == null) {
            return;
        }
        context.publishEvent(event);
    }

    /**
     * 发布事件
     *
     * @param event event object
     */
    public static void publishEvent(Object event) {
        if (context == null) {
            return;
        }
        context.publishEvent(event);
    }

}
