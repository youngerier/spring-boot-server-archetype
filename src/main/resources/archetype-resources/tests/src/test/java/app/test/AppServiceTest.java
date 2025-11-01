#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.test;

import ${package}.api.user.model.dto.UserDTO;
import ${package}.api.user.model.request.UserRegisterRequest;
import ${package}.api.user.service.UserService;
import ${package}.biz.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;


@Slf4j
@ContextConfiguration(classes = {AppServiceTest.TestConfig.class})
public class AppServiceTest extends AbstractDbServiceTest {

    @Autowired
    private UserService userService;

    private UserDTO user;



    @Configuration
    @Import({UserServiceImpl.class})
    static class TestConfig {

    }


}