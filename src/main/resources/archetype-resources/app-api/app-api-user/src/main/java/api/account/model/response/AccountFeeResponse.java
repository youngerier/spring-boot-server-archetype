#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.response;

import ${package}.core.enums.AccountFeeType;
import ${package}.core.enums.RateMathType;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountFeeResponse {
    private String id;
    private String updateTime;
    private String createTime;
    private String remarks;
    private AccountFeeType feeType;
    private String type;
    private String childFeeType;
    private String accountId;
    private RateMathType mathType;
    private BigDecimal rate;
    private BigDecimal collectionRate;
    private String startTime;
    private String endTime;
    private BigDecimal high;
    private BigDecimal low;
    private String provider;
    private String providerField;
}
