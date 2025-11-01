#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.member.api.rbac;

import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户权限查询控制器
 */
@Slf4j
@RestController
@RequestMapping(WebConstants.MEMBER_API_V1_PREFIX +"/rbac")
@RequiredArgsConstructor
@Tag(name = "用户权限查询", description = "用户端权限查询相关接口")
public class UserPermissionController {


}