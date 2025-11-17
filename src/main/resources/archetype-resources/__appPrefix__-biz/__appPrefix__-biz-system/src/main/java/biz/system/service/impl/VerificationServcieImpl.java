#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.service.impl;

import ${package}.core.enums.VerificationServcie;
import org.springframework.stereotype.Service;

@Service
public class VerificationServcieImpl  implements VerificationServcie {
    @Override
    public boolean sendVerificationCode(String email) {
        return false;
    }

    @Override
    public boolean check(String email, String captcha) {
        return true;
    }

    @Override
    public boolean needVerify(String email, String password) {
        return false;
    }

    @Override
    public boolean needVerify(String email) {
        return false;
    }

    @Override
    public void updateVerifyStatus(String email, boolean rememberMe) {

    }
}
