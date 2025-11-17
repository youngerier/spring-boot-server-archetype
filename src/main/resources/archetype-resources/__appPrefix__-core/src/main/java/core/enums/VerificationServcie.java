#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;


public interface VerificationServcie {

    /**
     * 发送验证码
     */
    boolean sendVerificationCode(String email);


    boolean check(String email, String captcha);

    /**
     * 是否需要验证
     */
    boolean needVerify(String email, String password);

    /**
     * 是否需要验证
     */
    boolean needVerify(String email);


    void updateVerifyStatus(String email, boolean rememberMe);
}
