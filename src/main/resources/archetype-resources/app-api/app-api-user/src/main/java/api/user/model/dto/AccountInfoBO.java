#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.dto;

import lombok.Data;

/**
 * @author yuzhiwei
 * {@code @date}  2025/10/13 16:41
 * {@code @description}
 **/
@Data
public class AccountInfoBO {
    private Long tenantId;
    private Long accountId;
    private String displayId;
    private String outerAccountId;
    private String infinityAccountBalanceId;
    private String budgetId;
}
