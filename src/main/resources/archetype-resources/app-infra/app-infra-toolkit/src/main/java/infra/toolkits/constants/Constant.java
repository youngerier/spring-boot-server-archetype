#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.constants;

import lombok.experimental.UtilityClass;

import java.util.Locale;

/**
 * @author litao
 */
@UtilityClass
public class Constant {
    public static final String TOTAL = "total";
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";
    public static final String ORDER_FIELD = "sort";
    public static final String ORDER = "order";
    public static final String ASC = "asc";

    public static final Integer HTTP_OK = 200;

    public static final String UTF_8 = "UTF-8";

    /**
     * zh_CN
     */
    public static final Locale ZH = new Locale("zh", "CN");

    /**
     * en_US
     */
    public static final Locale EN = new Locale("en", "US");

    /**
     * X Request ID
     */
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String X_TENANT_ID = "X-Tenant-Id";

    /**
     * 链路追踪id
     */
    public static final String TRACE_ID = "traceId";

    /**
     * 交易发起方
     */
    public static final String SOURCE = "source";
    /**
     * 交易接收方
     */
    public static final String DESTINATION = "destination";


    public static final String CONTENT = "content";
    public static final String NULL_UUID = "00000000-0000-0000-0000-000000000000";
    /**
     *
     */
    public static final String DATE_TYPE_DAY = "day";
    /**
     *
     */
    public static final String DATE_TYPE_WEEK = "week";
    /**
     *
     */
    public static final String DATE_TYPE_MONTH = "month";
    /**
     *
     */
    public static final String DATE_TYPE_YEAR = "year";
    /**
     *
     */
    public static final String DATE_TYPE_QUARTER = "quarter";

    /**
     * account id
     */
    public static final String ACCOUNT_ID_FIELD = "accountId";

    /**
     * wallet id
     */
    public static final String WALLET_ID_FIELD = "walletId";

    /**
     * delete time
     */
    public static final String DELETE_TIME_FIELD = "deleteTime";

    /**
     * create time
     */
    public static final String CREATE_TIME_FIELD = "createTime";
    /**
     *
     */
    public static final String TRANSACTION_TIME_FIELD = "transactionTime";

    public static final String ACCOUNT = "account";

    public static final String TRANSACTION = "transaction";

    public static final String EARNINGS = "earnings";

    public static final String AMOUNT = "amount";

    public static final String RELEASED = "released";

    public static final String GROUP_CARD = "GroupCard";

    public static final String OPEN_CARD = "OpenCard";

    public static final String ACTIVE = "Active";

    public static final String IN_ACTIVE = "Inactive";

    public static final String REFUND_COUNT = "RefundCount";

    public static final String REFUND_AMOUNT = "RefundAmount";

    public static final String RECHARGE_COUNT = "RechargeCount";

    public static final String RECHARGE_AMOUNT = "RechargeAmount";

    public static final String INCOMES = "Incomes";

    public static final String CARD_BIG_CAMEL = "Card";

    public static final String ACTIVATION = "Activation";

    public static final String KYB_PASS = "KybPass";

    public static final String ACCOUNT_LABEL_RULE = "AccountLabelRule";

    public static final String HEALTH = "health";

    public static final String REFUND = "refund";

    public static final String UNREFUND = "unrefund";

    public static final String PASSED = "Passed";



    public static final String PHONE = "phone";
    /**
     * sql limit 1
     */
    public static final String LIMIT_ONE = "LIMIT 1";


    /**
     * _update后缀
     */
    public static final String SUFFIX_UPDATE = "_update";

    public static final String TIME_TYPE = "timeType";


    public static final String CLEAR_TYPE = "clearType";

    public static final String CLEARED = "Cleared";

    public static final String INACTIVE = "Inactive";


    public static final String VIRTUAL_CARD = "VirtualCard";

    public static final String MULTI_CURRENCY_ACCOUNT = "MultiCurrencyAccount";

    public static final String EXPORT_PDF = "pdf";

    public static final String EXPORT_EXCEl = "excel";

    public static final String CODE_SIX = "666666";

    public static final String FINGERPRINT = "Fingerprint";

    public static final String MERCHANT_CUSTOMER = "merchantCustomer";

    /**
     * 短信验证码
     */
    public static final String SEND_SMS_NOTIFICATION = "SEND_SMS_NOTIFICATION:phone:%s:type:%s";

    /**
     * 邮箱验证码
     */
    public static final String SEND_EMAIL_NOTIFICATION = "white_label:SEND_EMAIL_NOTIFICATION:email:%s:type:%s";

    /**
     * 验证码失败次数统计
     */
    public static final String CHECK_CODE_FAIL_COUNT = "white_label:CHECK_CODE_FAIL_COUNT:PHONE_EMAIL:%s:type:%s";

    /**
     * 租户账户kyc
     */
    public static final String TENANT_CDD_KYC_CACHE = "TENANT_CDD_KYC_CACHE:tenantId:%s:accountId:%s";

}
