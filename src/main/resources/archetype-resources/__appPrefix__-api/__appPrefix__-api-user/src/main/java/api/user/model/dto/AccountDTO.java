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
public class AccountDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 归属租户: 运营用户为 0
     */
    private Long tenantId;

    /**
     * 用户介绍、备注
     */
    private String remark;

}
