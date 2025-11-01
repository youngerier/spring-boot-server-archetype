#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import lombok.Data;

@Data
public class AccountFeeDataResponse {
	private Integer code;
	private Boolean data;
	private String from;
	private String message;
}