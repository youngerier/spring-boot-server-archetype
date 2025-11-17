#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import java.util.List;
import lombok.Data;

@Data
public class RecordsItemResponse {
	private List<AccountFeeResponse> accountFees;
	private String createTime;
	private String accountType;
	private String id;
	private String verifiedName;
	private String displayId;
	private String type;
	private String verifiedNameEn;
	private String status;
}