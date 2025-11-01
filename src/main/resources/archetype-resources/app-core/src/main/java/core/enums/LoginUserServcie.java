#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;


public interface LoginUserServcie {

    LoginUser loadUserByUsernameAndTenantId(Long tenantId, String username);
}
