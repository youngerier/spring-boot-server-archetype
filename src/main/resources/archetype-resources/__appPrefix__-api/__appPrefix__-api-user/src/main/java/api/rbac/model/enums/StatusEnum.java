#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 状态枚举
 * @author wangping
 */
@Getter
public enum StatusEnum {
    DISABLED("DISABLED", "禁用"),
    ENABLED("ENABLED", "启用");

    @EnumValue
    private final String code;
    private final String desc;

    StatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}