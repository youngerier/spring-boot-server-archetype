#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemException extends RuntimeException {

    // 错误码
    private String code;
    // 错误信息
    private String message;

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SystemException(String message) {
        super(message);
        this.message = message;
    }

    public SystemException(String message, Exception e) {
        super(message);
        this.message = message;
    }
}
