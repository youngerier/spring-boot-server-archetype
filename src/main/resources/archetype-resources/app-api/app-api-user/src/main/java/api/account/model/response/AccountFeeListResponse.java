#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import lombok.Data;

@Data
public class AccountFeeListResponse {
	private Integer code;
	private AccountDeeListDataResponse data;
	private String message;
}