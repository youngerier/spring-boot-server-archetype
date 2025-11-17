#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;

import lombok.Data;

/**
 * @author martinjiang
 */
@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String verificationCode;
}
