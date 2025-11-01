#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 资源类型枚举
 */
@Getter
public enum ResourceTypeEnum {
    MENU("menu", "菜单"),
    BUTTON("button", "按钮"),
    API("api", "接口");

    @EnumValue
    private final String code;
    private final String desc;

    ResourceTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}