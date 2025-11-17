#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.dto;

import lombok.Data;

/**
 * @author john
 * {@code @date}  2025/10/17 18:44
 * {@code @description}
 **/
@Data
public class TenantConfigDTO {
    private String clientSecret;
}
