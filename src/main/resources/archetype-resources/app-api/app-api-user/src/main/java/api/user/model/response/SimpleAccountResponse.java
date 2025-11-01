#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author System
 * @since 1.0.0
 * 响应对象
 */
@Data
public class SimpleAccountResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -6077403771355899876L;

    /**
     * ID
     */
    private Long id;

    /**
     * 账号名称
     */
    private String username;

    /**
     *
     */
    private String displayId;
}
