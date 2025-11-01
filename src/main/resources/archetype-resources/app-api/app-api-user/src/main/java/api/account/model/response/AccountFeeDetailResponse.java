#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import java.util.List;
import lombok.Data;

@Data
public class AccountFeeDetailResponse{
	private Integer code;
	private List<AccountFeeDetailDataItem> data;
	private String message;
}