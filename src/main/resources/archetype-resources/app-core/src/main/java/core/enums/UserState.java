#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;


import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserState implements DescriptiveEnum<Integer> {
    ACTIVE(1, "已激活"),

    ;

    @EnumValue
    private final Integer code;

    private final String desc;
}