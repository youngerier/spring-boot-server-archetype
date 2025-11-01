#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.controller;


import ${package}.infra.business.Result;
import ${package}.starter.jwt.model.TokenResponse;
import ${package}.starter.jwt.token.request.PasswordLoginRequest;
import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端展示生成文档使用, 没有实际效果
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "登录管理", description = "用户登录")
public class LoginController {


    @PostMapping(value = WebConstants.ADMIN_API_V1_PREFIX + "/auth/login/PASSWORD")
    @Operation(summary = "管理员端密码登录")
    public Result<TokenResponse> loginAdminByPassword(@RequestBody @Validated PasswordLoginRequest request) {
        return Result.ok();
    }

    @PostMapping(value = WebConstants.MEMBER_API_V1_PREFIX + "/auth/login/PASSWORD")
    @Operation(summary = "用户端密码登录")
    public Result<TokenResponse> loginMemberByPassword(@RequestBody @Validated PasswordLoginRequest request) {
        return Result.ok();
    }

}
