#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 用户类型枚举
 * @author martinjiang
 */
@Getter
public enum UserTypeEnum {
    /**
     * 主用户
     */
    MASTER("Master"),

    /**
     * 机器人用户
     */
    ROBOT("Robot"),

    /**
     * 添加的管理员
     */
    ADMIN("Admin"),

    /**
     * 添加的员工
     */
    EMPLOYEE("Employee");
    @EnumValue
    private final String value;

    UserTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据字符串获取枚举值
     */
    public static UserTypeEnum fromValue(String value) {
        for (UserTypeEnum type : UserTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown UserTypeEnum value: " + value);
    }
}