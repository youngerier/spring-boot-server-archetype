#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class SecurityConfig {


    /**
     * 配置 PBKDF2 密码编码器
     * PBKDF2 是一种基于密码的密钥派生函数，通过迭代哈希+盐值增强安全性
     */
    @Bean
    public Pbkdf2PasswordEncoder pbkdf2PasswordEncoder() {
        // 可放配置文件中，不建议硬编码
        String secret = "Mn5DV8";
        // 默认即可（内部随机生成）
        int saltLength = 16;
        // 推荐：>=100,000（现代CPU下性能仍可接受）
        int iterations = 185000;
        return new Pbkdf2PasswordEncoder(
                secret,
                saltLength,
                iterations,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        );
    }

}