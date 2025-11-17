#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 角色类型枚举
 */
@Getter
public enum RoleTypeEnum {
    SYSTEM("SYSTEM", "系统角色"),
    BUSINESS("BUSINESS", "业务角色"),
    CUSTOM("CUSTOM", "自定义角色");

    @EnumValue
    private final String code;
    private final String desc;

    RoleTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}