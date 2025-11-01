#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;


import com.mybatisflex.annotation.EnumValue;
import lombok.Getter;

/**
 * @author martinjiang
 */

@Getter
public enum KycStatus {
    SYSTEM_DETECTION("SystemDetection", "待系统校验"),
    PENDING("Pending", "提交但未审核"),
    MANUAL_CONFIRMATION("ManualConfirmation", "待人工确认"),
    AWAIT_ADDITIONAL("AwaitAdditional", "待后补"),
    CHECK_ADDITIONAL("CheckAdditional", "检测后补"),
    REQUEST("Request", "驳回"),
    PASSED("Passed", "审核通过"),
    CANCELED("Canceled", "拒绝准入"),
    BLACKLISTED("Blacklisted", "禁止准入"),
    NA("Na", "未提交"),
    PROCESSING("Processing", "待审核"),
    ENABLED_NO_TRADING("EnabledNoTrading", "可用但不能交易"),
    AWAIT_ADD_QUESTION("AwaitAddQuestion", "待补充问卷"),
    AWAIT_SUBMIT_QUESTION("AwaitSubmitQuestion", "待提交问卷"),
    AWAIT_REVIEW_QUESTION("AwaitReviewQuestion", "待审核问卷"),
    REQUEST_QUESTION("RequestQuestion", "驳回问卷"),
    CRD_REJECTED("CRDRejected", "合规拒绝");
    /**
     * -- GETTER --
     *  获取英文状态码
     */
    @EnumValue
    private final String code;

    private final String description;

    KycStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据 code 获取对应枚举
     *
     * @param code 英文状态码
     * @return 对应的枚举，若无匹配返回 null
     */
    public static KycStatus fromCode(String code) {
        for (KycStatus status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        return null;
    }
}