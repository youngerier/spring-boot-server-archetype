#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.test;


import com.mybatisflex.spring.boot.MybatisFlexAutoConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

@SpringJUnitConfig
@Import({
        AbstractServiceTest.TestConfig.class,
//        MybatisConfiguration.class,
})
@ImportAutoConfiguration({
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        MybatisFlexAutoConfiguration.class,
        SqlInitializationAutoConfiguration.class,
        AbstractServiceTest.H2InitializationAutoConfiguration.class
})
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true)
@TestPropertySource(locations = {"classpath:/application-h2.properties", "classpath:application-test.properties"})
public class AbstractServiceTest {


    @Configuration
    @MapperScan("${package}.biz.*.dal.repository")
    static class TestConfig {

        @Bean
        @Primary
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @AllArgsConstructor
    @AutoConfiguration
    @AutoConfigureBefore(SqlInitializationAutoConfiguration.class)
    public static class H2InitializationAutoConfiguration {

        private final DataSource dataSource;

        @PostConstruct
        public void init() {
            System.out.println("x");
        }

    }
}
