#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.provider.authentication;

import com.alibaba.fastjson2.JSON;
import ${package}.core.enums.LoginUser;
import ${package}.core.enums.LoginUserServcie;
import ${package}.core.enums.VerificationServcie;
import ${package}.infra.business.AppType;
import ${package}.infra.business.JwtUser;
import ${package}.starter.jwt.token.JwtAuthenticationToken;
import ${package}.starter.jwt.token.LoginMethod;
import ${package}.starter.jwt.token.request.AbstractLoginRequest;
import ${package}.starter.jwt.token.request.PasswordLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final LoginUserServcie loginUserServcie;
    private final PasswordEncoder passwordEncoder;
    private final VerificationServcie verificationServcie;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AbstractLoginRequest principal = (AbstractLoginRequest) authentication.getPrincipal();

        if (Objects.equals(principal.getLoginMethod(), LoginMethod.PASSWORD)) {
            PasswordLoginRequest request = (PasswordLoginRequest) authentication.getPrincipal();
            AppType appType = request.getAppType();

            String password = authentication.getCredentials().toString();
            // Validate user credentials
            LoginUser user = null;
            try {
                user = loginUserServcie.loadUserByUsernameAndTenantId(request.getTenantId(), request.getUsername());
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid user or credentials");
            }
            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Invalid user or credentials");
            }


            this.verify(request, appType, user);

            JwtUser jwtUser = JwtUser.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .tenantId(user.getTenantId())
                    .build();
            return JwtAuthenticationToken.authenticated(jwtUser, null, Collections.emptyList());
        } else {
            throw new UnsupportedOperationException("unknown login method");
        }
    }

    void verify(PasswordLoginRequest request, AppType appType, LoginUser user) {
        if (Objects.equals(appType, AppType.MEMBER)) {
            if (user.isAdmin()) {
                // 管理员不能登录用户端
                throw new BadCredentialsException("Invalid login user type");
            }
            boolean needVerify = verificationServcie.needVerify(request.getUsername());
            if (needVerify) {
                if (StringUtils.isEmpty(request.getCaptcha())) {
                    throw new BadCredentialsException("Verification code required");
                }
                try {
                    if (!verificationServcie.check(request.getUsername(), request.getCaptcha())) {
                        throw new BadCredentialsException("Invalid verification code");
                    }
                } catch (Exception e) {
                    log.error("verification failed request:{}", JSON.toJSONString(request), e);
                    throw new BadCredentialsException("Invalid verification code");
                }
                // 更新 最后登录时间,rememberMe
                verificationServcie.updateVerifyStatus(request.getUsername(), request.getRememberMe());
            }
        }

        if (Objects.equals(appType, AppType.ADMIN)) {
            // 管理端登录 校验是否为管理员用户
            if (!user.isAdmin()) {
                throw new BadCredentialsException("Invalid login user type");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}