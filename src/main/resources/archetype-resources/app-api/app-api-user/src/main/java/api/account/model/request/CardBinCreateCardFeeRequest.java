#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.request;

import lombok.Data;

/**
 * @description @Date 2025/10/21 16:21
 * @author zhoubobing
 */
@Data
public class CardBinCreateCardFeeRequest {

    private String cardBinId;

    private String accountId;
}
