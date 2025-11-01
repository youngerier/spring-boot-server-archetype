#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 通用账户激活状态
 * @author martinjiang
 */
@Getter
public enum ActivationStatusEnum {
    /** 激活 */
    ACTIVE("Active"),
    /** 只读 */
    READONLY("Readonly"),
    /** 刚创建处理中 */
    PENDING("Pending"),
    /** 关停 */
    INACTIVE("Inactive"),
    /** 冻结 */
    FROZEN("Frozen"),
    /** 处理中 */
    PROCESSING("Processing"),
    /** 其他特殊情况 */
    OTHER("Other"),
    /** 清退 */
    CLEARED("Cleared"),
    /** 管控-只能由管理员解控 */
    CONTROL("Control");

    @EnumValue
    private final String value;

    ActivationStatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}