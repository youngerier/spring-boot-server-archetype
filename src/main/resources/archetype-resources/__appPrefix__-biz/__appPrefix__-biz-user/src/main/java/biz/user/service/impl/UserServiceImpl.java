#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.service.impl;

import ${package}.api.user.service.UserService;
import ${package}.biz.user.dal.repository.UserRepository;
import ${package}.core.enums.LoginUser;
import ${package}.core.enums.LoginUserServcie;
import ${package}.infra.business.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * @author martinjiang
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, LoginUserServcie {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public LoginUser loadUserByUsernameAndTenantId(Long tenantId, String username) {
        return null;
    }
}
