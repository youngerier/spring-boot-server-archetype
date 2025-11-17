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
public class AccountBalanceDataResponse {
    private Integer code;
    private Map<String, BigDecimal> data;
    private String from;
    private String message;
}
