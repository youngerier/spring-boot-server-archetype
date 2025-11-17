#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.rbac.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * RBAC资源树形结构DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RbacResourceTreeDTO extends RbacResourceDTO {
    /**
     * 子资源列表
     */
    private List<RbacResourceTreeDTO> children = new ArrayList<>();
}