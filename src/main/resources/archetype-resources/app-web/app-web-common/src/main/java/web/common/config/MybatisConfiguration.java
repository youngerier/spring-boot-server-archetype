#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.config;

import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@EnableTransactionManagement
@EnableAsync
@Configuration
@MapperScan({"${package}.biz.*.dal.mapper"})
public class MybatisConfiguration {

    @Bean
    public MyBatisFlexCustomizer myBatisFlexConfiguration() {
        return flexGlobalConfig -> {
            // 关闭 MyBatis Flex 启动横幅
            flexGlobalConfig.setPrintBanner(false);

            GlobalAutoFillListener globalAutoFillListener = new GlobalAutoFillListener();
            // 配置全局监听器，统一处理 create_time,update_time 字段的自动更新
            flexGlobalConfig.registerInsertListener(globalAutoFillListener, Object.class);
            flexGlobalConfig.registerUpdateListener(globalAutoFillListener, Object.class);
        };
    }

    public static class GlobalAutoFillListener implements InsertListener, UpdateListener {

        @Override
        public void onInsert(Object entity) {
            fillCreateTime(entity);
            fillUpdateTime(entity);
        }

        @Override
        public void onUpdate(Object entity) {
            fillUpdateTime(entity);
        }

        /**
         * 填充创建时间
         */
        private void fillCreateTime(Object entity) {
            try {
                Field createTimeField = ReflectionUtils.findField(entity.getClass(), "createTime");
                if (createTimeField != null && ReflectionUtils.getField(createTimeField, entity) == null) {
                    ReflectionUtils.makeAccessible(createTimeField);
                    ReflectionUtils.setField(createTimeField, entity, LocalDateTime.now());
                }
            } catch (Exception e) {
                // 忽略异常，不影响主流程
            }
        }

        /**
         * 填充更新时间
         */
        private void fillUpdateTime(Object entity) {
            try {
                Field updateTimeField = ReflectionUtils.findField(entity.getClass(), "updateTime");
                if (updateTimeField != null) {
                    ReflectionUtils.makeAccessible(updateTimeField);
                    ReflectionUtils.setField(updateTimeField, entity, LocalDateTime.now());
                }
            } catch (Exception e) {
                // 忽略异常，不影响主流程
            }
        }
    }

}
