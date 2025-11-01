#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.service;

import ${package}.api.user.model.dto.AccountUserDTO;
import org.jetbrains.annotations.NotNull;

/**
 * @author martinjiang
 */
public interface AccountUserService {

    AccountUserDTO findByUserId(@NotNull Long userId);
}
