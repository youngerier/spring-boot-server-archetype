#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.test.repository;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.mybatisflex.spring.boot.MybatisFlexAutoConfiguration;
import ${package}.web.common.config.MybatisConfiguration;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@ActiveProfiles("h2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        MybatisConfiguration.class,
})
@ImportAutoConfiguration({
        MybatisFlexAutoConfiguration.class
})
public class RepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void testJdbcQuery() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t_user", Integer.class);
        assertThat(count).isEqualTo(1);
    }


    @Test
    public void testAlias() {
        JsonAlias jsonAlias = new JsonAlias();
        jsonAlias.setName("test");
        System.out.println(JSON.toJSONString(jsonAlias));
        var s = JSON.parseObject("""
                {"user_name":"test"}
                """, JsonAlias.class);
        var s1 = JSON.parseObject("""
                {
                    "user_name":"test",
                    "name":"name_test"
                }
                """, JsonAlias.class);
        System.out.println("s = " + s);
    }

    @Data
    public static class JsonAlias {
        @JSONField(alternateNames = {"username", "user_name"})
        private String name;
    }


}
