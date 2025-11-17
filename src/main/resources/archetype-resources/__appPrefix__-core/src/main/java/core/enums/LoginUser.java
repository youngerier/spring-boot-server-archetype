#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

import java.time.LocalDateTime;

public interface LoginUser {
    String getUsername();

    Long getTenantId();

    Long getUserId();

    String getPassword();

    boolean isAdmin();

    LocalDateTime getLastLoginTime();

    Boolean getRememberMe();
}
