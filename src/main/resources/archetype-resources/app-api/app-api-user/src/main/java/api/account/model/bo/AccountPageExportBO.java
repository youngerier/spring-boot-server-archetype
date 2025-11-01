#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.account.model.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author martinjiang
 */
@Data
public class AccountPageExportBO {

    @Schema(description = "用户ID")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_DISPLAY_ID_0}", index = 0)
    private String displayId;

    @Schema(description = "用户名称")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_VERIFIED_NAME_1}", index = 1)
    private String nickname;

    @Schema(description = "注册时间")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_CREATE_TIME_2}", index = 2)
    private String createTime;

    @Schema(description = "注册邮箱")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_EMAIL_3}", index = 3)
    private String email;

    @Schema(description = "kyc状态")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_KYC_STATUS_4}", index = 4)
    private String kycStatus;

    @Schema(description = "kyc状态更新时间")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_KYC_UPDATE_TIME_5}", index = 5)
    private String kycUpdateTime;

    @Schema(description = "kyc拒绝原因")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_KYC_REJECT_REASON_6}", index = 6)
    private String kycRejectReason;

    @Schema(description = "总资产估值(${symbol_dollar})")
    @ExcelProperty(value = "{TENANT_ADMIN_EXPORT_ACCOUNT_PAGE_TOTAL_USD_7}", index = 7)
    private String totalUsd;

}
