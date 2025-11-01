#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AccountFeeApplyRequest {
	/**
	 * 账户ID
	 */
	@Schema(
			description = "",
			example = "d84a92b5-8fb9-4052-80a3-1b1594ebcf84"
	)
	private String accountId;
	/**
	 * 申请列表
	 */
	@Schema(description = "申请列表", example = "[]")
	private List<ApplyListItemRequest> applyList;
}