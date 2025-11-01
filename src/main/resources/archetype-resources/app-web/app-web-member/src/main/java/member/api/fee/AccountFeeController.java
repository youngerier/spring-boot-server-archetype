#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.member.api.fee;

import ${package}.api.account.model.request.AccountFeeObjRequest;
import ${package}.api.account.model.request.CardBinCreateCardFeeRequest;
import ${package}.api.account.model.response.AccountFeeBbjResponse;
import ${package}.api.account.model.response.AccountFeeDetailDataItem;
import ${package}.api.account.model.response.RecordsItemResponse;
import ${package}.api.account.service.AccountFeeService;
import ${package}.infra.business.Result;
import ${package}.web.common.constants.WebConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description @Date 2025/9/25 12:22
 * @author zhoubobing
 */
@Slf4j
@RestController
@RequestMapping(WebConstants.MEMBER_API_V1_PREFIX + "/account-fee")
@RequiredArgsConstructor
@Tag(name = "费用", description = "费用管理")
public class AccountFeeController {

    @Resource private AccountFeeService accountFeeService;

    /**
     * 查询账户费用列表
     *
     * @param param 查询参数
     * @return Result<AccountDeeListDataResponse> 返回账户费用列表数据
     */
    @PostMapping("/list")
    @Operation(summary = "查询", description = "查询资源列表")
    public Result<List<RecordsItemResponse>> listAccountFee(
            @RequestBody AccountFeeObjRequest param) {
        AccountFeeBbjResponse accountDeeListDataResponse =
                accountFeeService.listAccountFeeByAccountId(param);
        return Result.ok(accountDeeListDataResponse.getData());
    }

    /**
     * 获取账户费用详情
     *
     * @return Result<List<AccountFeeDetailDataItem>> 返回账户费用详情数据
     */
    @PostMapping("/details")
    @Operation(summary = "详情", description = "查询资源详情")
    public Result<List<AccountFeeDetailDataItem>> getAccountFeeDetails() {
        List<AccountFeeDetailDataItem> accountFeeDetails = accountFeeService.getAccountFeeDetails();
        return Result.ok(accountFeeDetails);
    }

    @PostMapping("/card-bin-create-card-fee")
    @Operation(description = "根据账户ID和卡BIN查询开卡费率")
    public Result<AccountFeeDetailDataItem> getCardBinCreateCardFee(
            @Valid @RequestBody CardBinCreateCardFeeRequest request) {
        return Result.ok(accountFeeService.getCardBinCreateCardFee(request));
    }
}
