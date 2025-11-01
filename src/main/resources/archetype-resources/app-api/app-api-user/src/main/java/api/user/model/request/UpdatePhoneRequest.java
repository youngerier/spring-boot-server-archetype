#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;

import lombok.Data;

/**
 * @author martinjiang
 */
@Data
public class UpdatePhoneRequest {

    private String newPhone;

    private String verificationCode;
}
