#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginMethod {
    PASSWORD(1, "密码登录"),
    MOBILE_PHONE(2, "手机验证码"),
    EMAIL(3, "邮箱验证码"),
    ;

    private final Integer code;

    private final String desc;
}