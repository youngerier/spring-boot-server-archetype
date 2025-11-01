#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.controller;

import ${package}.web.common.constants.WebConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:58
 * 控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.COMMON_API_V1_PREFIX +"/tenant-profile")
@Slf4j
@Tag(name = "租户个性化配置", description = "租户个性化配置")
public class TenantProfileController {

}
