#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * @author LiTao litao@qbitnetwork.com
 */
@Getter
public enum RateMathType {

    /**
     * 按笔计算
     */
    Count("Count"),

    /**
     * 百分比
     */
    Percent("Percent");

    @JsonValue
    private final String value;

    RateMathType(String value) {
        this.value = value;
    }
}
