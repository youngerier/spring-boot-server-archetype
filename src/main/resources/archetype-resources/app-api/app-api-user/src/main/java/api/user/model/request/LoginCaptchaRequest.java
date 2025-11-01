#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginCaptchaRequest {
    @Schema(description = "email")
    private String email;

    @Schema(description = "rememberMe")
    private Boolean rememberMe;
}
