#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplyListItemRequest {
	/**
	 * 渠道名称
	 */
	@Schema(description = "渠道名称", example = "BlueBanc")
	private String provider;
	/**
	 * 收益比例
	 */
	@Schema(description = "收益比例", example = "0.05")
	private BigDecimal collectionRate;
	/**
	 * ID
	 */
	@Schema(description = "ID", example = "1")
	private String id;
	/**
	 * 渠道字段
	 */
	@Schema(description = "渠道字段", example = "537100")
	private String providerField;
}