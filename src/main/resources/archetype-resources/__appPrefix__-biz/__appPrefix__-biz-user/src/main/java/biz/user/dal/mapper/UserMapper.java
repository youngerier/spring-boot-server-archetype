#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.dal.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import ${package}.api.user.model.dto.UserDTO;
import ${package}.biz.user.dal.entity.User;
import ${package}.biz.user.dal.entity.table.UserTableRefs;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author martinjiang
 */
public interface UserMapper extends BaseMapper<User> {

    default User getUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        UserTableRefs userTableRefs = UserTableRefs.user;

        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(userTableRefs)
                .where(userTableRefs.nickname.eq(username));
        return selectOneByQuery(queryWrapper);
    }

    default User getUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            return null;
        }
        UserTableRefs userTableRefs = UserTableRefs.user;

        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(userTableRefs)
                .where(userTableRefs.nickname.eq(userDTO.getNickname()))
                .and(userTableRefs.id.eq(userDTO.getId()))
                .and(userTableRefs.email.eq(userDTO.getEmail()))
                ;
        return selectOneByQuery(queryWrapper);
    }

    default List<User> selectBatchByIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return List.of();
        }
        UserTableRefs userTableRefs = UserTableRefs.user;
        
        QueryWrapper queryWrapper = QueryWrapper.create()
                .from(userTableRefs)
                .where(userTableRefs.id.in(userIds));
        return selectListByQuery(queryWrapper);
    }
}
