#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.request;

import lombok.Data;

import java.util.List;

/**
 * @author martinjiang
 */
@Data
public class AccountBalanceDataRequest {

    private List<String> accountIds;
}
