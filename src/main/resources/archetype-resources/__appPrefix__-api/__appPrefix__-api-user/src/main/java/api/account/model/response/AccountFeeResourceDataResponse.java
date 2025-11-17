#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import lombok.Data;

/**
 * @description @Date 2025/10/15 10:18
 * @author zhoubobing
 */
@Data
public class AccountFeeResourceDataResponse {
    private String from;
    private AccountFeeResourceObjDataResponse data;
    private Integer code;
    private String message;

    @Data
    public static class AccountFeeResourceObjDataResponse {
        private ApiCardPermissionResponse data;
        private Integer code;
        private String message;
    }
}
