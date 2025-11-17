#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.exception;

public interface ExceptionCodeService {
    String getMessage(String code, Object... args);
}
