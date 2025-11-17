#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author martinjiang
 */
@Data
public class OcrResponse {
    private Integer code;
    private Object data;
    private String from;
    private String message;
}
