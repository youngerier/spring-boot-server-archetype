#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * 账户具体类型/角色
 * @author martinjiang
 */
@Getter
public enum AccountGeneralTypeEnum {
    /** 商户账户 */
    MERCHANT("Merchant");
    @EnumValue
    private final String value;

    AccountGeneralTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}