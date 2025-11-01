#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountFeeBbjResponse {
    private String code;

    private String message;

    private List<RecordsItemResponse> data;
}
