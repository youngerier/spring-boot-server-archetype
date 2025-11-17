#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.interfaces;

import ${package}.api.user.model.response.SimpleAccountResponse;

/**
 * IAccount
 *
 * @author <a href="mailto:litao@qbitnetwork.com">Kratos</a>
 */
public interface IAccount {
    /**
     * 获取账户ID
     *
     * @return 账号ID
     */
    Long getAccountId();

    /**
     * 设置账号
     *
     * @param account {@link SimpleAccountResponse}
     */
    void setAccount(SimpleAccountResponse account);
}
