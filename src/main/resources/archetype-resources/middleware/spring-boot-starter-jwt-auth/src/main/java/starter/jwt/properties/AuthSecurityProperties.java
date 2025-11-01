#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT configuration properties
 */
@Data
@ConfigurationProperties(prefix = "auth.security")
public class AuthSecurityProperties {

    /**
     * Enable JWT authentication filter
     */
    private boolean enabled = true;

    /**
     * Login path patterns
     * "/{appType}/api/v1/auth/login/{loginMethod}"
     */
    private String loginPathPattern;

    /**
     * Paths that don't require authentication
     */
    private String[] publicPaths;

    /**
     * Paths that require authentication but no specific permissions
     */
    private String[] authenticatedOnlyPaths;

    private String[] authPaths;
}