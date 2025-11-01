#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;


import ${package}.core.enums.KycStatus;
import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.DefaultOrderField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author martinjiang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountPageRequest extends AbstractPageQuery<DefaultOrderField> {
    private String displayId;
    private String username;
    private String email;
    private List<LocalDateTime> registerTimeFroms;
    private List<LocalDateTime> kycUpdateTimeFrom;
    private List<KycStatus> kycStatus;
    private Long tenantId;

}
