#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import lombok.Data;

import java.util.List;

@Data
public class AccountDeeListDataResponse {
    /** 总记录数 */
    private String total;

    /** 当前页码 */
    private String current;

    /** 总页数 */
    private String pages;

    /** 每页记录数 */
    private String size;

    /** 记录列表 */
    private List<RecordsItemResponse> records;
}
