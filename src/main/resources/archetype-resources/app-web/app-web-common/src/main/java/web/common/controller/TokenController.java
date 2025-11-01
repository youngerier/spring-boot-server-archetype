#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.controller;

import ${package}.infra.business.JwtService;
import ${package}.infra.business.JwtUser;
import ${package}.infra.business.Result;
import ${package}.starter.jwt.model.TokenResponse;
import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Token控制器
 * 处理token刷新等操作
 */
@Slf4j
@RestController
@RequestMapping(WebConstants.COMMON_API_V1_PREFIX + "/auth/token")
@RequiredArgsConstructor
@Tag(name = "Token管理", description = "处理token刷新等操作")
public class TokenController {

    private final JwtService jwtService;

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 新的访问令牌和刷新令牌
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新访问令牌", description = "使用refresh_token获取新的access_token")
    public Result<TokenResponse> refreshToken(@RequestParam("refresh_token") String refreshToken) {
        // 验证refresh_token
        if (!jwtService.validateRefreshToken(refreshToken)) {
            return Result.fail("invalid refresh token");
        }

        // 从refresh_token中提取用户名
        JwtUser jwtUser = jwtService.extractRefreshTokenUsername(refreshToken);

        // 生成新的access_token和refresh_token
        String newAccessToken = jwtService.generateToken(jwtUser);
        String newRefreshToken = jwtService.generateRefreshToken(jwtUser);

        // 创建响应
        TokenResponse response = TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        return Result.ok(response);
    }
}