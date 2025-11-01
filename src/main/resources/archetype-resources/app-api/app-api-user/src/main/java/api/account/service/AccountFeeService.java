#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.service;

import ${package}.api.account.model.request.AccountFeeApplyRequest;
import ${package}.api.account.model.request.AccountFeeListRequest;
import ${package}.api.account.model.request.AccountFeeObjRequest;
import ${package}.api.account.model.request.CardBinCreateCardFeeRequest;
import ${package}.api.account.model.response.AccountFeeBbjResponse;
import ${package}.api.account.model.response.AccountFeeDetailDataItem;
import ${package}.api.account.model.response.ApiCardPermissionResponse;
import ${package}.api.account.model.response.RecordsItemResponse;

import jakarta.validation.Valid;

import java.util.List;

/**
 * @description @Date 2025/9/25 16:00
 * @author zhoubobing
 */
public interface AccountFeeService {

    /**
     * 账户费用申请
     *
     * @param param param
     * @return Object
     */
    Boolean accountFeeApply(AccountFeeApplyRequest param);

    /**
     * 账户费用列表
     *
     * @param param param
     * @return Object
     */
    List<RecordsItemResponse> listAccountFee(AccountFeeListRequest param);

    /**
     * 账户费用详情
     *
     * @return Object
     */
    List<AccountFeeDetailDataItem> getAccountFeeDetails();

    AccountFeeBbjResponse listAccountFeeByAccountId(AccountFeeObjRequest param);

    /**
     * 获取API卡权限
     *
     * @return ApiCardPermissionResponse
     */
    ApiCardPermissionResponse getApiCardPermission();

    /**
     * 获取卡BIN创建卡费用详情
     *
     * @param request 请求参数
     * @return 费用详情
     */
    AccountFeeDetailDataItem getCardBinCreateCardFee(@Valid CardBinCreateCardFeeRequest request);
}
