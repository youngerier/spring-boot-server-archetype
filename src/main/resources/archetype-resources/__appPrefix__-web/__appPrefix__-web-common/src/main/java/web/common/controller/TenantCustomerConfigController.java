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
 * @Date: 2025/10/13 10:43
 * 租户配置表
 * 控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(WebConstants.COMMON_API_V1_PREFIX +"/tenant-customer-config")
@Slf4j
@Tag(name = "租户配置", description = "处理租户配置相关操作")
public class TenantCustomerConfigController {

}
