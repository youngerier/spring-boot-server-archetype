#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class WhiteLabelAccountVO {

    @Schema(description = "kyc申请中客户数")
    private BigDecimal applyKycCustomerNum = BigDecimal.ZERO;

    @Schema(description = "KYC通过商户数")
    private BigDecimal kycPassedCustomerNum = BigDecimal.ZERO;
}
