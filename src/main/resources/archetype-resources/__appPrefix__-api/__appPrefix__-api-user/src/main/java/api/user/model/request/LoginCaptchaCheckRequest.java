#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginCaptchaCheckRequest {
    @Schema(description = "email")
    @NotEmpty(message = "email cannot be empty")
    private String email;

    private String password;
}
