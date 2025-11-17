#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT configuration properties
 */
@Data
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT access token secret key
     */
    private String secretKey = "booster111111111000000000booster111111111000000000booster111111111000000000";

    /**
     * JWT refresh token secret key
     */
    private String refreshSecretKey = "refresh111111111000000000refresh111111111000000000refresh111111111000000000";

    /**
     * Access token expiration days
     */
    private int accessTokenExpirationDays = 10;

    /**
     * Refresh token expiration days
     */
    private int refreshTokenExpirationDays = 30;

}