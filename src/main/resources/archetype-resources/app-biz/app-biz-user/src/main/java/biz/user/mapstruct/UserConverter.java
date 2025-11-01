#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.mapstruct;

import ${package}.api.user.model.dto.UserDTO;
import ${package}.biz.user.dal.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDTO convertToUserDTO(User user);

    User convertToUser(UserDTO userDTO);
}
