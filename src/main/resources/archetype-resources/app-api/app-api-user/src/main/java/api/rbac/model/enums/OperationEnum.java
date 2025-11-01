#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 操作类型枚举
 */
@Getter
public enum OperationEnum {
    READ("READ", "读取"),
    WRITE("WRITE", "写入"),
    DELETE("DELETE", "删除"),
    EXECUTE("EXECUTE", "执行"),
    MANAGE("MANAGE", "管理");

    @EnumValue
    private final String code;
    private final String desc;

    OperationEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}