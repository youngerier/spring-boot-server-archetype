#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author martinjiang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUserDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private Long accountId;

    private Long userId;

    private Integer version;

    private String remarks;

    private Long tenantId;

    private LocalDateTime updateTime;

    private LocalDateTime deleteTime;
}
