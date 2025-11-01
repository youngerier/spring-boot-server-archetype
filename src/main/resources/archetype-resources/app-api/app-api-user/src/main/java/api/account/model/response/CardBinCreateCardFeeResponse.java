#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import lombok.Data;

/**
 * @description @Date 2025/10/21 16:23
 * @author zhoubobing
 */
@Data
public class CardBinCreateCardFeeResponse {

    private AccountFeeDetailDataItem data;
}
