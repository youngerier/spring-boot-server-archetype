#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 授权类型枚举
 */
@Getter
public enum GrantTypeEnum {
    GRANT("GRANT", "授权"),
    DENY("DENY", "拒绝");

    @EnumValue
    private final String code;
    private final String desc;

    GrantTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}