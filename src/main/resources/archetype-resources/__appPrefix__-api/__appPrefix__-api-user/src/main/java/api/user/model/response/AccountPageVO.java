#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.response;


import ${package}.core.enums.KycStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author martinjiang
 */
@Data
public class AccountPageVO {
    private String outAccountId;
    private String outKycId;
    private Long accountId;
    private String displayId;
    private Long userId;
    private String nickname;
    private Date createTime;
    private String email;
    private KycStatus kycStatus;
    private Date kycUpdateTime;
    private String rejectReason;
    private BigDecimal totalAssetUsd;
}
