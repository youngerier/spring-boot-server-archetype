#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.service;

import ${package}.api.user.model.dto.UserCredentialDTO;
import ${package}.api.user.model.request.UserCredentialQuery;
import ${package}.infra.toolkits.page.Pagination;

import java.util.List;

/**
 * @author System
 *   @since 1.0.0
 * 服务接口
 */
public interface UserCredentialService {
    /**
     * 创建UserCredential
     * @param userCredentialDTO UserCredential数据传输对象
     * @return 创建的UserCredential对象
     */
    UserCredentialDTO createUserCredential(UserCredentialDTO userCredentialDTO);

    /**
     * 根据ID查询UserCredential
     * @param id 主键ID
     * @return 对应的UserCredential对象
     */
    UserCredentialDTO getUserCredentialById(long id);

    /**
     * 查询所有UserCredential
     * @return UserCredential对象列表
     */
    List<UserCredentialDTO> queryUserCredentials(UserCredentialQuery query);

    /**
     * 分页查询UserCredential
     * @return UserCredential对象列表
     */
    Pagination<UserCredentialDTO> pageQueryUserCredentials(UserCredentialQuery query);

    /**
     * 更新UserCredential
     * @param id 主键ID
     * @param userCredentialDTO UserCredential数据传输对象
     * @return 更新后的UserCredential对象
     */
    UserCredentialDTO updateUserCredential(long id, UserCredentialDTO userCredentialDTO);

    /**
     * 删除UserCredential
     * @param id 主键ID
     * @return 是否删除成功
     */
    boolean deleteUserCredential(long id);
}
