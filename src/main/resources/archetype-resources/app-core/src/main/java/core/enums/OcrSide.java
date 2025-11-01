#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * @author martinjiang
 */
@Getter
public enum OcrSide {
    /**
     * 正面
     */
    FACE("face"),

    /**
     * 背面
     */
    BACK("back");
    @EnumValue
    private final String value;

    OcrSide(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OcrSide fromValue(String value) {
        for (OcrSide side : values()) {
            if (side.value.equalsIgnoreCase(value)) {
                return side;
            }
        }
        throw new IllegalArgumentException("Unknown OcrSideEnum value: " + value);
    }
}
