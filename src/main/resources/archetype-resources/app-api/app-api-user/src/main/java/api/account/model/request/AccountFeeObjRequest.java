#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.request;

import io.swagger.v3.oas.annotations.Hidden;

import lombok.Data;

import java.util.List;

@Data
public class AccountFeeObjRequest {

    private List<String> accountIds;

    @Hidden private String accountId;

    /** 费用类型列表 */
    private List<String> feeType;
}
