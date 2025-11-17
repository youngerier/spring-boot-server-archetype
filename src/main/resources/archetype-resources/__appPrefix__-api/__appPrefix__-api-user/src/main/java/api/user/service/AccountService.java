#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.service;

import ${package}.api.user.interfaces.IAccount;
import ${package}.api.user.model.dto.AccountDTO;
import ${package}.api.user.model.dto.AccountInfoBO;
import ${package}.api.user.model.request.AccountPageRequest;
import ${package}.api.user.model.response.AccountPageVO;
import ${package}.infra.toolkits.page.IPagination;
import ${package}.infra.toolkits.page.Pagination;

import java.util.Collection;
import java.util.Map;

/**
 * @author martinjiang
 */
public interface AccountService {
    /**
     * 为指定用户创建个人账户（Individual Account）。
     *
     * @param userId   指定的用户ID，用于关联账户
     * @param tenantId 指定租户ID，用于多租户环境下关联账户
     * @return 创建完成的 {@link AccountDTO} 对象，包含账户基本信息和关联信息
     */
    AccountDTO createIndividualAccount(Long userId, Long tenantId);

    /**
     * 根据指定的请求参数获取账户的分页列表。
     *
     * @param request the pagination and filter criteria for querying accounts
     *                查询账户的分页和过滤条件
     * @return a {@link Pagination} object containing a list of {@link AccountPageVO} and pagination metadata
     * 包含 {@link AccountPageVO} 列表和分页信息的 {@link Pagination} 对象
     */
    IPagination<AccountPageVO> accountPage(AccountPageRequest request);

    /**
     * Exports account data based on the specified request parameters.
     * 根据指定的请求参数导出账户数据。
     */
    String exportAccount(AccountPageRequest request);

    AccountInfoBO selectAccountInfoById(Long accountId);

    /**
     * 批量根据accountId列表查询对应的userId映射
     * 如果一个account对应多个user，只返回其中一个userId
     *
     * @param accountIds accountId列表
     * @return accountId到userId的映射Map
     */
    Map<Long, Long> batchGetUserIdByAccountIds(Collection<Long> accountIds);

    /**
     * 根据三方账号ID查询账号信息
     *
     * @param accountId 三方账号ID
     * @return {@link AccountInfoBO}
     */
    AccountInfoBO getByOuterAccountId(String accountId);

    /**
     * 关联账号信息
     *
     * @param data 数据集
     */
    void relation(Collection<? extends IAccount> data);
}
