#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author martinjiang
 */
@Data
public class AccountPageBO {
    private String displayId;
    private String nickname;
    private LocalDateTime createTime;
    private String email;
    private String kycStatus;
    private LocalDateTime kycUpdateTime;
    private String rejectReason;
    private BigDecimal totalAssetUsd;
}
