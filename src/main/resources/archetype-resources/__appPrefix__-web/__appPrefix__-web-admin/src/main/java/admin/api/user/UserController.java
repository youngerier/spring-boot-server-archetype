#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.admin.api.user;

import ${package}.api.user.model.dto.UserDTO;
import ${package}.api.user.model.request.AddAdminUserRequest;
import ${package}.api.user.model.request.ResetPasswordRequest;
import ${package}.api.user.model.request.UpdateEmailRequest;
import ${package}.api.user.model.request.UpdatePasswordRequest;
import ${package}.api.user.service.UserService;
import ${package}.infra.business.Result;
import ${package}.infra.business.context.CurrentUserContext;
import ${package}.infra.toolkits.constants.Constant;
import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author martinjiang
 */
@Slf4j
@RestController
@RequestMapping(WebConstants.ADMIN_API_V1_PREFIX + "/user")
@RequiredArgsConstructor
@Tag(name = "admin用户管理", description = "admin用户管理相关接口")
public class UserController {


    private final UserService userService;

    @PostMapping(value = "profile")
    @Operation(summary = "profile")
    public Result<UserDTO> profile() {
        Long userId = CurrentUserContext.getUserId();
        return Result.ok();
    }


}
