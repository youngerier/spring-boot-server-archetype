#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.request;

import ${package}.core.enums.AccountFeeType;

import lombok.Data;

import java.util.List;

@Data
public class AccountFeeListRequest {

    private String accountId;

    /** 费用类型列表 */
    private List<AccountFeeType> feeType;
}
