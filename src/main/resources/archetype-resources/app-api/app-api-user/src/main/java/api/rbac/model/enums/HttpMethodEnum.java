#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.enums;

import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * HTTP方法枚举
 */
@Getter
public enum HttpMethodEnum {
    GET("GET", "查询"),
    POST("POST", "新增"),
    PUT("PUT", "修改"),
    DELETE("DELETE", "删除"),
    PATCH("PATCH", "部分更新");

    @EnumValue
    private final String code;
    private final String desc;

    HttpMethodEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}