#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * @author martinjiang
 */
@Getter
public enum AccountOwnerTypeEnum {
    /** ä¸ªäººè´¦æ?? */
    INDIVIDUAL("Individual"),

    /** ä¼?ä¸?è´¦æ?? */
    BUSINESS("Business");

    @EnumValue
    private final String value;

    AccountOwnerTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}