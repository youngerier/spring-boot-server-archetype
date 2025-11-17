#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.member.api;

import ${package}.api.user.service.UserService;
import ${package}.web.common.aop.Log;
import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(WebConstants.MEMBER_API_V1_PREFIX + "/test")
@RestController
public class ApplicationController {

    @Value("${symbol_dollar}{spring.profiles.active:dev}")
    private String active;

    @Autowired
    private UserService userService;

    @Log
    @Operation(summary = "普通body请求")
    @GetMapping("env")
    public String index() {
        log.info("active = {}", active);
        return active;
    }
}
