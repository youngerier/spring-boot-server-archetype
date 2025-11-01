#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.admin.api.rbac;

import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户角色管理控制器
 */
@Slf4j
@RestController
@RequestMapping(WebConstants.ADMIN_API_V1_PREFIX + "/rbac/user-roles")
@RequiredArgsConstructor
@Tag(name = "用户角色管理", description = "用户角色关联管理相关接口")
public class UserRoleController {


}