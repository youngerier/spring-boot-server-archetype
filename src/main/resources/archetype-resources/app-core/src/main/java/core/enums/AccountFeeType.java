#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

import java.util.List;

/**
 * @author LiTao litao@qbitnetwork.com
 */
@Getter
public enum AccountFeeType {
    //${symbol_pound}region 全球账户基础费率
    /**
     * 结汇汇率优惠
     */
    SettleRate("SettleRate"),
    /**
     * 付款服务费折扣
     */
    PaymentServiceOffers("PaymentServiceOffers"),
    /**
     * 全球账户入金手续费2
     */
    GlobalAccountInbound2("GlobalAccountInbound2"),
    /**
     * 大额阈值费率
     */
    GlobalAccountExceedThresholdRate("GlobalAccountExceedThresholdRate"),
    /**
     * 账户互转（原内部转账）手续费
     */
    GlobalAccountTransferFee("GlobalAccountTransferFee"),

    /**
     * 大额阈值
     */
    GlobalAccountExceedThreshold("GlobalAccountExceedThreshold"),
    /**
     * 基础币种汇率加点
     */
    BaseCurrencyRateAdd("BaseCurrencyRateAdd"),
    /**
     * CNH
     */
    CNHMarkupFee("CNHMarkupFee"),

    /**
     * 认证时的markUpFee
     */
    AuthorizationMarkUpFee("AuthorizationMarkUpFee"),
    /**
     * 认证时的markup fee Highnote
     */
    AuthorizationMarkupHN("AuthorizationMarkupHN"),
    /**
     * 制卡费 rp
     */
    MakeCardFeeRP("MakeCardFeeRP"),

    /**
     * 全球账户入金主流币种
     */
    GlobalInboundCurrencyMain_CC("GlobalInboundCurrencyMain_CC"),
    GlobalInboundCurrencyMain_CL("GlobalInboundCurrencyMain_CL"),
    GlobalInboundCurrencyMain_PY("GlobalInboundCurrencyMain_PY"),
    GlobalInboundCurrencyMain_L2("GlobalInboundCurrencyMain_L2"),
    GlobalInboundCurrencyMain_HF("GlobalInboundCurrencyMain_HF"),
    /**
     * 全球账户入金其他币种
     */
    GlobalInboundCurrencyOther_CC("GlobalInboundCurrencyOther_CC"),
    GlobalInboundCurrencyOther_CL("GlobalInboundCurrencyOther_CL"),
    GlobalInboundCurrencyOther_PY("GlobalInboundCurrencyOther_PY"),
    GlobalInboundCurrencyOther_L2("GlobalInboundCurrencyOther_L2"),
    GlobalInboundCurrencyOther_HF("GlobalInboundCurrencyOther_HF"),
    /**
     * 全球账户入金收费模式2（暂时学名逐笔）
     */
    GlobalInbound2_CC("GlobalInbound2_CC"),
    GlobalInbound2_CL("GlobalInbound2_CL"),
    GlobalInbound2_PY("GlobalInbound2_PY"),
    GlobalInbound2_L2("GlobalInbound2_L2"),
    GlobalInbound2_HF("GlobalInbound2_HF"),
    /**
     * 全球账户账户转入手续费
     */
    GlobalTransferIn_CC("GlobalTransferIn_CC"),
    GlobalTransferIn_CL("GlobalTransferIn_CL"),
    GlobalTransferIn_PY("GlobalTransferIn_PY"),
    GlobalTransferIn_L2("GlobalTransferIn_L2"),
    GlobalTransferIn_HF("GlobalTransferIn_HF"),
    GlobalTransferIn_QB("GlobalTransferIn_QB"),
    GlobalTransferIn_ZB("GlobalTransferIn_ZB"),
    GlobalTransferIn_RD("GlobalTransferIn_RD"),
    GlobalTransferIn_RF("GlobalTransferIn_RF"),
    GlobalTransferIn_EP("GlobalTransferIn_EP"),

    /**
     * 最小入金手续费
     */
    GlobalInboundMinFee_CC("GlobalInboundMinFee_CC"),
    GlobalInboundMinFee_CL("GlobalInboundMinFee_CL"),
    GlobalInboundMinFee_PY("GlobalInboundMinFee_PY"),
    GlobalInboundMinFee_L2("GlobalInboundMinFee_L2"),
    GlobalInboundMinFee_HF("GlobalInboundMinFee_HF"),
    GlobalInboundMinFee_RD("GlobalInboundMinFee_RD"),
    GlobalInboundMinFee_QB("GlobalInboundMinFee_QB"),
    GlobalInboundMinFee_EP("GlobalInboundMinFee_EP"),
    GlobalInboundMinFee_RF("GlobalInboundMinFee_RF"),
    GlobalInboundMinFee_QM("GlobalInboundMinFee_QM"),
    GlobalInboundMinFee_ZB("GlobalInboundMinFee_ZB"),

    //${symbol_pound}endregion 全球账户基础费率

    /**
     * 全球账户开户费
     * 3.31 版本变更此费用用于全球账户开户费
     */
    InternationalCharging("InternationalCharging"),

    /**
     * 携程退款费 基础费
     */
    TripLinkBaseRefundFee("TripLinkBaseRefundFee"),

    /**
     * 认证时 跨境 RP fee
     */
    AuthorizationReapFee("AuthorizationReapFee"),

    /**
     * -- CNY结算入金手续费
     */
    CNYSettleInboundFee("CNYSettleInboundFee"),

    /**
     * 全球账户转入量子账户手续费
     */
    GlobalAccountToQbitWallet("GlobalAccountToQbitWallet"),

    /**
     * 人民币账户转全球账户手续费
     */
    CNYSettleTransferOut("CNYSettleTransferOut"),
    /**
     * 人民币账户提现手续费T+0
     */
    CNYSettleWithdrawT0("CNYSettleWithdrawT0"),
    /**
     * 人民币账户提现手续费T+1
     */
    CNYSettleWithdrawT1("CNYSettleWithdrawT1"),

    //${symbol_pound}region 全球账户付款与结汇
    /**
     * 结汇手续费（服贸客户）
     */
    SettleServiceFeeFM("SettleServiceFeeFM"),
    /**
     * 结汇手续费（电商和外贸客户）
     */
    SettleServiceFeeB2B("SettleServiceFeeB2B"),
    /**
     * SWIFT - SHA
     */
    SwiftSHA("SwiftSHA"),
    /**
     * SWIFT - ACO - Tier 1&Tier 2
     */
    SwiftACOTier1Tier2("SwiftACOTier1Tier2"),
    /**
     * SWIFT - ACO - Tier 3
     */
    SwiftACOTier3("SwiftACOTier3"),
    /**
     * SWIFT - ACO - Tier 4
     */
    SwiftACOTier4("SwiftACOTier4"),
    /**
     * SWIFT - ACO - Tier 5
     */
    SwiftACOTier5("SwiftACOTier5"),
    /**
     * Local payment fee 1
     */
    LocalPaymentFee1("LocalPaymentFee1"),
    /**
     * Local payment fee 2
     */
    LocalPaymentFee2("LocalPaymentFee2"),
    /**
     * Local payment fee 3（RON、HK-HKD）
     */
    LocalPaymentFee3("LocalPaymentFee3"),
    /**
     * Local payment fee 4
     */
    LocalPaymentFee4("LocalPaymentFee4"),
    /**
     * Local payment fee 5 (PHP)
     */
    LocalPaymentFee5("LocalPaymentFee5"),
    /**
     * Local payment fee 6 (HK- USD, JPY)
     */
    LocalPaymentFee6("LocalPaymentFee6"),
    //${symbol_pound}endregion 全球账户付款与结汇

    /**
     * 拒付交易费
     */
    DeclineFee("DeclineFee"),
    /**
     * 量子账户充值
     */
    AccountDeposit("AccountDeposit"),
    /**
     * 加密资产交易手续费（非美国）
     */
    CryptoTransfer("CryptoTransfer"),
    /**
     * 创建子钱包费用(USD)
     */
    CRYPTO_CREATION_SUB_WALLET("CryptoCreationSubWallet"),
    /**
     * 子钱包免费数
     */
    CRYPTO_SUB_WALLET_FREE_COUNT("CryptoSubWalletFreeCount"),
    /**
     * 子钱包最大创建数
     */
    CRYPTO_SUB_WALLET_MAX_COUNT("CryptoSubWalletMaxCount"),
    /**
     * 子钱包最大创建数-API
     */
    API_CRYPTO_SUB_WALLET_MAX_COUNT("APICryptoSubWalletMaxCount"),

    /**
     * 银行卡出金费率
     */
    CRYPTO_TRANSFER_RATE("CryptoTransferRate"),

    /**
     * 加密法币代付markup
     */
    CRYPTO_ASSET_PAYOUT_MARKUP("CryptoAssetPayoutMarkup"),

    /**
     * 统一付款 手续费加点
     */
    PAYMENT_MARKUP_FEE("PaymentMarkUpFee"),

    /**
     * 统一付款 用户手续费加点
     */
    PAYMENT_ACCOUNT_MARKUP_FEE("PaymentAccountMarkupFee"),

    /**
     * 统一付款 假期额外附加点
     */
    PAYMENT_ACCOUNT_HOLIDAY_ADDITIONAL_FEE("PaymentAccountHolidayAdditionalFee"),

    /**
     * 您独自承担服务费
     */
    CRYPTO_TRANSFER_OUR("CryptoTransferOur"),

    /**
     * 您与收款方共同承担服务费
     */
    CRYPTO_TRANSFER_SHA("CryptoTransferSha"),

    /**
     * 稳定币转账换汇费
     */
    CRYPTO_STABLECOIN_EXCHANGE_TRANSFER("CryptoStablecoinExchangeTransfer"),

    /**
     * 加密资产交易手续费:
     * 交易量<${symbol_dollar}1,000,000
     */
    CryptoExchange("CryptoExchange"),
    /**
     * 加密资产交易手续费：
     * ${symbol_dollar}1,000,000≤交易量<${symbol_dollar}3,000,000
     */
    CryptoExchangeFeeL1("CryptoExchangeFeeL1"),
    /**
     * 加密资产交易手续费
     * ${symbol_dollar}3,000,000≤交易量<${symbol_dollar}10,000,000
     */
    CryptoExchangeFeeL2("CryptoExchangeFeeL2"),
    /**
     * 加密资产交易手续费:
     * 交易量≥${symbol_dollar}10,000,000
     */
    CryptoExchangeFeeL3("CryptoExchangeFeeL3"),

    /**
     * 加密资产法币交易汇率加点(USD-USDC/USDT)
     */
    CryptoExchangeLegalTenderRate("CryptoExchangeLegalTenderRate"),

    /**
     * 加密资产稳定币交易汇率加点(USDT-USDC)
     */
    CryptoExchangeStableRate("CryptoExchangeStableRate"),

    /**
     * 加密资产主流币交易汇率加点(BTC/ETH-USDC/USDT/USD)
     */
    CryptoExchangeMainStreamRate("CryptoExchangeMainStreamRate"),

    /**
     * API子账户加密资产费率-加密资产代付markup
     */
    APICustomerCryptoAssetPayoutMarkup("APICustomerCryptoAssetPayoutMarkup"),

    /**
     * 入金增加手续费字段，可支持按需配置 2022-07-04
     */
    CryptoUSDInbound("CryptoUSDInbound"),

    /**
     * 加密资产USD/USDC钱包入金
     */
    CryptoUSDCInbound("CryptoUSDCInbound"),

    /**
     * 理财交易手续费(不满7天)
     */
    FinancingTransfer("FinancingTransfer"),

    /**
     * 退款率第一档 40%以内
     */
    TransactionRefundFeeL1("TransactionRefundFeeL1"),
    /**
     * 退款率第二档 70%以内
     */
    TransactionRefundFeeL2("TransactionRefundFeeL2"),
    /**
     * 退款率第三档 大于等于70%
     */
    TransactionRefundFeeL3("TransactionRefundFeeL3"),

    /**
     * 转出跨链手续费
     */
    TransferOutCrossChain("TransferOutCrossChain"),

    /**
     * 交易退款费
     */
    TransactionRefundFeeRefund("TransactionRefundFeeRefund"),

    /**
     * 加密资产USD充值手续费
     */
    CryptoDepositFeeFromQuantumAccount("CryptoDepositFeeFromQuantumAccount"),

    /**
     * API子账户加密资产费率-加密资产交易手续费(<${symbol_dollar}1M)
     */
    APICustomerCryptoExchange("APICustomerCryptoExchange"),

    /**
     * API子账户加密资产费率-加密资产交易手续费(${symbol_dollar}1M-3M)
     */
    APICustomerCryptoExchangeFeeL1("APICustomerCryptoExchangeFeeL1"),

    /**
     * API子账户加密资产费率-加密资产交易手续费(${symbol_dollar}3M-10M)
     */
    APICustomerCryptoExchangeFeeL2("APICustomerCryptoExchangeFeeL2"),

    /**
     * API子账户加密资产费率-加密资产交易手续费(≥10M)
     */
    APICustomerCryptoExchangeFeeL3("APICustomerCryptoExchangeFeeL3"),

    /**
     * API子账户加密资产费率-加密资产法币交易汇率加点(USD-USDC)
     */
    APICustomerCryptoExchangeLegalTenderRate("APICustomerCryptoExchangeLegalTenderRate"),

    /**
     * API子账户加密资产费率-加密资产交易markup fee（USD/BTC/ETH）
     */
    APICustomerCryptoExchangeMainStreamRate("APICustomerCryptoExchangeMainStreamRate"),

    /**
     * API子账户加密资产费率-加密资产交易markup fee（USDT-USDC/USD）
     */
    APICustomerCryptoExchangeStableRate("APICustomerCryptoExchangeStableRate"),

    /**
     * API子账户加密资产费率-加密资产转出手续费
     */
    APICustomerCryptoTransfer("APICustomerCryptoTransfer"),

    /**
     * API子账户加密资产费率-加密资产USD钱包入金
     */
    APICustomerCryptoUSDInbound("APICustomerCryptoUSDInbound"),

    /**
     * API子账户加密资产费率-加密资产USDC钱包入金
     */
    APICustomerCryptoUSDCInbound("APICustomerCryptoUSDCInbound"),

    /**
     * API子账户加密资产费率-跨链转账手续费
     */
    APICustomerTransferOutCrossChain("APICustomerTransferOutCrossChain"),

    /**
     * API子账户加密资产费率-量子账户转出到USD钱包手续费
     */
    APICustomerCryptoDepositFeeFromQuantumAccount("APICustomerCryptoDepositFeeFromQuantumAccount"),

    /**
     * 量子卡开卡费
     */
    OpenCard("OpenCard"),

    /**
     * markUpFee-NM
     */
    AuthorizationMarkupNM("AuthorizationMarkupNM"),

    /**
     * markUpFee-RP
     */
    AuthorizationMarkupRP("AuthorizationMarkupRP"),

    /**
     * markUpFee-TP
     */
    AuthorizationMarkupTP("AuthorizationMarkupTP"),

    /**
     * ATM fee Reap
     */
    AuthorizationATMRP("AuthorizationATMRP"),

    /**
     * Apple Pay fee Reap
     */
    AuthorizationApplePayRP("AuthorizationApplePayRP"),

    /**
     * 实体卡邮寄费
     */
    ShoppingFee("ShoppingFee"),

    /**
     * 交易授权费 Reap
     */
    AuthorizationFeeRP("AuthorizationFeeRP"),

    /**
     * 认证时的markup fee Thepenny
     */
    AuthorizationMarkupTPNew("AuthorizationMarkupTPNew"),

    /**
     * 交易撤销费
     */
    TransactionReversalFee("TransactionReversalFee"),

    /**
     * 订单退款费
     */
    TransactionRefundFee("TransactionRefundFee"),

    OpenReapCard("OpenReapCard"),

    NiumNotActiveFee("NiumNotActiveFee"),

    /**
     * 量子卡免费开卡数上限
     */
    FreeOpenCard("FreeOpenCard"),

    /**
     * settlement fee - dom（%）
     */
    QuantumCardSettlementFeeDomRate("QuantumCardSettlementFeeDomRate"),
    /**
     * settlement fee - int（%）
     */
    QuantumCardSettlementFeeIntRate("QuantumCardSettlementFeeIntRate"),
    /**
     * settlement fee - dom(${symbol_dollar}/笔)
     */
    QuantumCardSettlementFeeDom("QuantumCardSettlementFeeDom"),
    /**
     * settlement fee - int(${symbol_dollar}/笔)
     */
    QuantumCardSettlementFeeInt("QuantumCardSettlementFeeInt"),

    /**
     * 子账户开卡手续费
     */
    ChildAccountOpenCard("ChildAccountOpenCard"),

    ChildAccountOpenReapCard("ChildAccountOpenReapCard"),

    /**
     * 优选卡-NR消费返现(原量子账户返现2023-10-10修改，将老数据映射给 优选卡- NR消费返现)
     */
    QUANTUM_ACCOUNT_CASH_BACK("QuantumAccountCashBack", true),

    /**
     * RP卡-NR消费返现
     */
    QUANTUM_CARD_RP_CONSUMPTION_CASH_BACK("QuantumCardRPConsumptionCashBack", true),

    /**
     * RC卡-NR消费返现
     */
    QUANTUM_CARD_RC_CONSUMPTION_CASH_BACK("QuantumCardRCConsumptionCashBack", true),

    /**
     * NM卡-NR消费返现
     */
    QUANTUM_CARD_NM_CONSUMPTION_CASH_BACK("QuantumCardNMConsumptionCashBack", true),

    /**
     * QI卡-NR消费返现
     */
    QUANTUM_CARD_QI_CONSUMPTION_CASH_BACK("QuantumCardQIConsumptionCashBack", true),
    /**
     * BB卡-NR消费返现
     */
    QUANTUM_CARD_BB_CONSUMPTION_CASH_BACK("QuantumCardBBConsumptionCashBack", true),
    /**
     * RP卡/优选卡-NR消费返现
     */
    QUANTUM_CARD_IPR_AND_RP_CONSUMPTION_CASH_BACK("QuantumCardIPRAndRPConsumptionCashBack", true),
    /**
     * RC卡/优选卡-NR消费返现
     */
    QUANTUM_CARD_IPR_AND_RC_CONSUMPTION_CASH_BACK("QuantumCardIPRAndRCConsumptionCashBack", true),
    /**
     * QI卡/优选卡-NR消费返现
     */
    QUANTUM_CARD_IPR_AND_QI_CONSUMPTION_CASH_BACK("QuantumCardIPRAndQIConsumptionCashBack", true),
    /**
     * RC/QI卡/优选卡-NR消费返现
     */
    QUANTUM_CARD_IPR_AND_RC_AND_QI_CONSUMPTION_CASH_BACK("QuantumCardIPRAndRCAndQIConsumptionCashBack", true),
    /**
     * 优选卡、NM卡-开卡返现(原量子卡开卡返现2023-10-10修改, 将老数据映射给 优选卡、NM卡-开卡返现)
     */
    QUANTUM_CREATE_CARD_CASH_BACK("QuantumCreateCardCashBack", true),

    /**
     * IPR卡-开卡返现
     */
    QUANTUM_IPR_CREATE_CARD_CASH_BACK("QuantumIPRCreateCardCashBack", true),

    /**
     * NM卡-开卡返现
     */
    QUANTUM_NM_CREATE_CARD_CASH_BACK("QuantumNMCreateCardCashBack", true),

    /**
     * RP卡-开卡返现
     */
    QUANTUM_RP_CREATE_CARD_CASH_BACK("QuantumRPCreateCardCashBack", true),

    /**
     * RC卡-开卡返现
     */
    QUANTUM_RC_CREATE_CARD_CASH_BACK("QuantumRCCreateCardCashBack", true),

    /**
     * QI卡-开卡返现
     */
    QUANTUM_QI_CREATE_CARD_CASH_BACK("QuantumQICreateCardCashBack", true),
    /**
     * BB卡-开卡返现
     */
    QUANTUM_BB_CREATE_CARD_CASH_BACK("QuantumBBCreateCardCashBack", true),

    /**
     * 全球账户返现-结汇
     */
    GLOBAL_ACCOUNT_SETTLE_CASH_BACK("GlobalAccountSettleCashBack", true),

    /**
     * 全球账户返现-外币付款
     */
    GLOBAL_ACCOUNT_OTHER_PAYMENT_CASH_BACK("GlobalAccountOtherPaymentCashBack", true),

    /**
     * CC-全球账户入金返现
     */
    GLOBAL_ACCOUNT_CC_INBOUND_CASH_BACK("GlobalAccountCCInboundCashBack", true),

    /**
     * CL-全球账户入金返现
     */
    GLOBAL_ACCOUNT_CL_INBOUND_CASH_BACK("GlobalAccountCLInboundCashBack", true),

    /**
     * PY-全球账户入金返现
     */
    GLOBAL_ACCOUNT_PY_INBOUND_CASH_BACK("GlobalAccountPYInboundCashBack", true),
    /**
     * RD-全球账户入金返现
     */
    GLOBAL_ACCOUNT_RD_INBOUND_CASH_BACK("GlobalAccountRDInboundCashBack", true),
    /**
     * EP-全球账户入金返现
     */
    GLOBAL_ACCOUNT_EP_INBOUND_CASH_BACK("GlobalAccountEPInboundCashBack", true),

    /**
     * 加密资产USD交易返现
     */
    CRYPTO_ASSET_USD_TRADE_CASH_BACK("CryptoAssetUSDTradeCashBack", true),

    /**
     * 粒子理财2.0技术服务费
     */
    FUND_TECHNICAL_SERVICE_FEE("FundTechnicalServiceFee"),

    /**
     * 全球账户最大可开数量
     */
    GLOBAL_ACCOUNT_MAX_OPEN_COUNT("GlobalAccountMaxOpenCount"),

    /**
     * 全球账户免费开户数
     */
    GLOBAL_ACCOUNT_FREE_COUNT("GlobalAccountFreeCount"),

    /**
     * 全球账户开户费
     */
    GLOBAL_ACCOUNT_CREATE_FEE("GlobalAccountCreateFee"),

    CNY_DEPOSIT_FEE("CNYDepositFee"),

    /**
     * web3合伙人加密资产交易提现阶梯手续费
     */
    ASSETS_ACCOUNT_TRADING("AssetsAccountTrading", true),
    /**
     * web3合伙人全球账户开户提现阶梯手续费
     */
    GLOBAL_ACCOUNT_OPEN("GlobalAccountOpen", true),
    /**
     * web3合伙人全球账户充值提现阶梯手续费
     */
    GLOBAL_ACCOUNT_DEPOSIT("GlobalAccountDeposit", true),
    /**
     * web3合伙人量子账户充值提现阶梯手续费
     */
    QBIT_CARD_ACCOUNT_DEPOSIT("QbitCardAccountDeposit", true),

    /**
     * 渠道方-客户激活返佣配置
     */
    CHANNEL_CUSTOMER_ACTIVATION("ChannelCustomerActivation", true),

    // 新版量子卡fee type 部分延续之前的枚举
    OPEN_CARD("OpenCard"),
    QUANTUM_CARD_MARKUP_FEE_PERCENTAGE("QuantumCardMarkUpFeePercentage"),
    QUANTUM_CARD_MARKUP_FEE_FIXED_VALUE("QuantumCardMarkUpFeeFixedValue"),
    QUANTUM_CARD_CROSS_BORDER_FEE_BASE_RATE("QuantumCardCrossBorderFeeBaseRate"),
    QUANTUM_CARD_FX_MARKUP_FEE_RATE("QuantumCardFxMarkupFeeRate"),
    QUANTUM_CARD_TRANSACTION_REVERSAL_FEE("QuantumCardTransactionReversalFee"),
    TRANSACTION_REVERSAL_FEE("TransactionReversalFee"),
    TRANSACTION_REFUND_FEE("TransactionRefundFee"),
    TRANSACTION_REFUND_FEE_REFUND("TransactionRefundFeeRefund"),
    QUANTUM_CARD_NOT_ACTIVE_FEE("QuantumCardNotActiveFee"),
    CHILD_ACCOUNT_OPEN_CARD("ChildAccountOpenCard"),
    QUANTUM_CARD_AUTHORIZATION_FEE("QuantumCardAuthorizationFee"),
    QUANTUM_CARD_ATM_FEE("QuantumCardATMFee"),
    QUANTUM_CARD_APPLE_PAY_FEE("QuantumCardApplePayFee"),
    QUANTUM_CARD_MAKE_CARD_FEE("QuantumCardMakeCardFee"),
    QUANTUM_CARD_METAL_MAKE_CARD_FEE("QuantumCardMetalMakeCardFee"),
    SHOPPING_FEE("ShoppingFee"),
    FREE_OPEN_CARD("FreeOpenCard"),
    DECLINE_FEE_MERCHANT_ACCOUNT("DeclineFeeMerchantAccount"),
    DECLINE_FEE_API_ACCOUNT("DeclineFeeAPIAccount"),
    /**
     * 交易授权费(Domestic)(${symbol_dollar}/笔) -阶梯收费
     */
    Quantum_Card_Authorization_Fee_By_Domestic("QuantumCardAuthorizationFeeByDomestic"),
    /**
     * 交易授权费(International)(${symbol_dollar}/笔)-阶梯收费
     */
    Quantum_Card_Authorization_Fee_By_International("QuantumCardAuthorizationFeeByInternational"),

    /**
     * 国际银行转账手续费
     */
    HKD_SWIFT_SHA_FEE("HkdSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    GBP_SWIFT_OUR_FEE("GbpSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    GBP_SWIFT_SHA_FEE("GbpSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    EUR_SWIFT_OUR_FEE("EurSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    EUR_SWIFT_SHA_FEE("EurSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    AUD_SWIFT_OUR_FEE("AudSwiftOurFee"),

    /**
     * CNH国际银行OUR手续费
     */
    CNH_SWIFT_OUR_FEE("CnhSwiftOurFee"),

    /**
     * CNH国际银行SHA手续费
     */
    CNH_SWIFT_SHA_FEE("CnhSwiftShaFee"),
    /**
     * 香港本地清算网络支付手续费
     */
    USD_CHATS_FEE("UsdChatsFee"),
    /**
     * 香港本地清算网络支付手续费
     */
    HKD_CHATS_FEE("HkdChatsFee"),

    /**
     * 香港本地清算网络支付手续费
     */
    CNH_CHATS_FEE("CnhChatsFee"),
    /**
     * 英国本地清算网络支付手续费
     */
    GBP_FPS_FEE("GbpFpsFee"),
    /**
     * 欧洲本地清算网络支付手续费
     */
    EUR_SEPA_FEE("EurSepaFee"),
    /**
     * 澳洲本地清算网络手续费
     */
    AUD_AUS_PAY_NET_FEE("AudAusPayNetFee"),
    /**
     * 迪拜本地清算网络手续费
     */
    AED_LOCAL_FEE("AedLocalFee"),
    /**
     * 以色列本地清算网络手续费
     */
    ILS_LOCAL_FEE("IlsLocalFee"),
    /**
     * 加拿大本地清算网络手续费
     */
    CAD_EFT_FEE("CadEftFee"),
    /**
     * 新加坡本地清算网络手续费
     */
    SGD_FAST_FEE("SgdFastFee"),
    /**
     * 马来西亚本地清算网络手续费
     */
    MYR_IBG_FEE("MyrIbgFee"),
    /**
     * 日本本地清算网络手续费
     */
    JPY_LOCAL_FEE("JpyLocalFee"),
    /**
     * 美国本地清算网络支付手续费
     */
    USD_WIRE_FEE("UsdWireFee"),
    /**
     * 国际银行转账手续费
     */
    AUD_SWIFT_SHA_FEE("AudSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    AED_SWIFT_OUR_FEE("AedSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    AED_SWIFT_SHA_FEE("AedSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    ILS_SWIFT_OUR_FEE("IlsSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    ILS_SWIFT_SHA_FEE("IlsSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    CAD_SWIFT_OUR_FEE("CadSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    CAD_SWIFT_SHA_FEE("CadSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    SGD_SWIFT_OUR_FEE("SgdSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    SGD_SWIFT_SHA_FEE("SgdSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    JPY_SWIFT_OUR_FEE("JpySwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    JPY_SWIFT_SHA_FEE("JpySwiftShaFee"),
    /**
     * 美国本地清算网络支付手续费
     */
    USD_ACH_FEE("UsdAchFee"),
    /**
     * 国际银行转账手续费
     */
    USD_SWIFT_OUR_FEE("UsdSwiftOurFee"),
    /**
     * 国际银行转账手续费
     */
    USD_SWIFT_SHA_FEE("UsdSwiftShaFee"),
    /**
     * 国际银行转账手续费
     */
    HKD_SWIFT_OUR_FEE("HkdSwiftOurFee"),

    ALL_SWIFT_OUR_FEE("AllSwiftOurFee"),

    ALL_SWIFT_SHA_FEE("AllSwiftShaFee"),

    /**
     * 泰国本地转账手续费
     */
    THB_LOCAL_FEE("ThbLocalFee"),

    /**
     * 瑞典本地转账手续费
     */
    SEK_ACH_FEE("SekAchFee"),

    /**
     * 挪威本地转账手续费
     */
    NOK_ACH_FEE("NokAchFee"),

    /**
     * @deprecated 全球账户入金手续费
     * - 注意：这里只对客户弃用，代码离还要作为默认fee使用
     * <p>
     * 渠道方账户fee申请时迁移
     */
    GlobalAccountInbound("GlobalAccountInbound"),
    /**
     * 渠道方账户fee申请时迁移
     * 最低结汇金额
     */
    MinSettleUSD("MinSettleUSD"),
    /**
     * 渠道方账户fee申请时迁移
     * 最低结汇金额
     *
     * @deprecated
     */
    MinSettleEUR("MinSettleEUR"),
    /**
     * 渠道方账户fee申请时迁移
     * 最低结汇金额
     *
     * @deprecated
     */
    MinSettleGBP("MinSettleGBP"),
    /**
     * @deprecated 付款服务费率
     * 对客户弃用，代码离还要作为默认fee使用
     */
    SettleServiceFee("SettleServiceFee"),

    // ------------------------------------api client 量子卡月付------------------------------------->
    /**
     * 账户充值
     */
    ACCOUNT_DEPOSIT_CAAS("AccountDeposit_Caas"),

    /**
     * 子账户开卡手续费(${symbol_dollar}/张)
     */
    CHILD_ACCOUNT_OPEN_CARD_CAAS("ChildAccountOpenCard_Caas"),

    /**
     * 开卡
     */
    OPEN_CARD_CAAS("OpenCard_Caas"),

    /**
     * 商户账户交易失败手续费（阶梯收费）
     */
    DECLINE_FEE_MERCHANT_ACCOUNT_CAAS("DeclineFeeMerchantAccount_Caas"),

    /**
     * Apple Pay服务费（%）
     */
    QUANTUM_CARD_APPLE_PAY_FEE_CAAS("QuantumCardApplePayFee_Caas"),

    /**
     * ATM取现费（%）
     */
    QUANTUM_CARD_ATM_FEE_CAAS("QuantumCardATMFee_Caas"),

    /**
     * 交易授权费（国内）（${symbol_dollar}/笔）-阶梯收费
     */
    QUANTUM_CARD_AUTHORIZATION_FEE_BY_DOMESTIC_CAAS("QuantumCardAuthorizationFeeByDomestic_Caas"),

    /**
     * 交易授权费（国际）（${symbol_dollar}/笔）-阶梯收费
     */
    QUANTUM_CARD_AUTHORIZATION_FEE_BY_INTERNATIONAL_CAAS("QuantumCardAuthorizationFeeByInternational_Caas"),

    /**
     * 跨境交易基础费率（%）
     */
    QUANTUM_CARD_CROSS_BORDER_FEE_BASE_RATE_CAAS("QuantumCardCrossBorderFeeBaseRate_Caas"),

    /**
     * 汇率加成费率（fx markup）（%）
     */
    QUANTUM_CARD_FX_MARKUP_FEE_RATE_CAAS("QuantumCardFxMarkupFeeRate_Caas"),

    /**
     * 实体卡制卡费（${symbol_dollar}/张）
     */
    QUANTUM_CARD_MAKE_CARD_FEE_CAAS("QuantumCardMakeCardFee_Caas"),

    /**
     * markUpFee 单笔
     */
    QUANTUM_CARD_MARK_UP_FEE_FIXED_VALUE_CAAS("QuantumCardMarkUpFeeFixedValue_Caas"),

    /**
     * markUpFee（%）
     */
    QUANTUM_CARD_MARK_UP_FEE_PERCENTAGE_CAAS("QuantumCardMarkUpFeePercentage_Caas"),

    /**
     * 金属实体卡制卡费
     */
    QUANTUM_CARD_METAL_MAKE_CARD_FEE_CAAS("QuantumCardMetalMakeCardFee_Caas"),

    /**
     * 非活跃用卡费（${symbol_dollar}/张）
     */
    QUANTUM_CARD_NOT_ACTIVE_FEE_CAAS("QuantumCardNotActiveFee_Caas"),

    /**
     * 交易撤销费（${symbol_dollar}/笔）
     */
    QUANTUM_CARD_TRANSACTION_REVERSAL_FEE_CAAS("QuantumCardTransactionReversalFee_Caas"),

    /**
     * 邮寄费
     */
    SHOPPING_FEE_CAAS("ShoppingFee_Caas"),

    /**
     * 交易退款费（${symbol_dollar}/笔）
     */
    TRANSACTION_REFUND_FEE_CAAS("TransactionRefundFee_Caas"),

    /**
     * 退款客户退款费（%）
     */
    TRANSACTION_REFUND_FEE_REFUND_CAAS("TransactionRefundFeeRefund_Caas"),

    /**
     * API账户交易失败手续费
     */
    DECLINE_FEE_API_ACCOUNT_CAAS("DeclineFeeAPIAccount_Caas"),

    /**
     * 活跃卡fee
     */
    MONTHLY_ACTIVE_CARD_FEE_CAAS("MonthlyActiveCardFee_Caas"),

    /**
     * QuantumCardSettlementFeeDomRate
     */
    QUANTUM_CARD_SETTLEMENT_FEE_BY_DOM_RATE_CAAS("QuantumCardSettlementFeeDomRate_Caas"),
    /**
     * QuantumCardSettlementFeeIntRate
     */
    QUANTUM_CARD_SETTLEMENT_FEE_BY_INT_RATE_CAAS("QuantumCardSettlementFeeIntRate_Caas"),
    /**
     * QuantumCardSettlementFeeDom
     */
    QUANTUM_CARD_SETTLEMENT_FEE_BY_DOM_CAAS("QuantumCardSettlementFeeDom_Caas"),
    /**
     * QuantumCardSettlementFeeInt
     */
    QUANTUM_CARD_SETTLEMENT_FEE_BY_INT_CAAS("QuantumCardSettlementFeeInt_Caas"),
    ;


    public static final List<AccountFeeType> COUNT_FEES = List.of(
            CryptoTransfer,
            CRYPTO_TRANSFER_SHA,
            CRYPTO_TRANSFER_OUR
    );
    /**
     * api 月付手续费类型——量子卡
     */
    public static final List<AccountFeeType> API_MONTH_QUANTUM_CARD_FEE_TYPE = List.of(
            ACCOUNT_DEPOSIT_CAAS,
            CHILD_ACCOUNT_OPEN_CARD_CAAS,
            OPEN_CARD_CAAS,
            DECLINE_FEE_MERCHANT_ACCOUNT_CAAS,
            QUANTUM_CARD_APPLE_PAY_FEE_CAAS,
            QUANTUM_CARD_ATM_FEE_CAAS,
            QUANTUM_CARD_AUTHORIZATION_FEE_BY_DOMESTIC_CAAS,
            QUANTUM_CARD_AUTHORIZATION_FEE_BY_INTERNATIONAL_CAAS,
            QUANTUM_CARD_CROSS_BORDER_FEE_BASE_RATE_CAAS,
            QUANTUM_CARD_FX_MARKUP_FEE_RATE_CAAS,
            QUANTUM_CARD_MAKE_CARD_FEE_CAAS,
            QUANTUM_CARD_MARK_UP_FEE_FIXED_VALUE_CAAS,
            QUANTUM_CARD_MARK_UP_FEE_PERCENTAGE_CAAS,
            QUANTUM_CARD_METAL_MAKE_CARD_FEE_CAAS,
            QUANTUM_CARD_NOT_ACTIVE_FEE_CAAS,
            QUANTUM_CARD_TRANSACTION_REVERSAL_FEE_CAAS,
            SHOPPING_FEE_CAAS,
            TRANSACTION_REFUND_FEE_CAAS,
            TRANSACTION_REFUND_FEE_REFUND_CAAS,
            DECLINE_FEE_API_ACCOUNT_CAAS,
            MONTHLY_ACTIVE_CARD_FEE_CAAS,
            QUANTUM_CARD_SETTLEMENT_FEE_BY_DOM_RATE_CAAS,
            QUANTUM_CARD_SETTLEMENT_FEE_BY_INT_RATE_CAAS,
            QUANTUM_CARD_SETTLEMENT_FEE_BY_DOM_CAAS,
            QUANTUM_CARD_SETTLEMENT_FEE_BY_INT_CAAS
    );

    /**
     * 这里是卡类查询的费率
     */
    public static final List<String> CARD_FEES = List.of(
            AccountDeposit.getValue(),
            OPEN_CARD.getValue(),
            QUANTUM_CARD_MARKUP_FEE_PERCENTAGE.getValue(),
            QUANTUM_CARD_MARKUP_FEE_FIXED_VALUE.getValue(),
            TRANSACTION_REVERSAL_FEE.getValue(),
            TRANSACTION_REFUND_FEE.getValue(),
            TRANSACTION_REFUND_FEE_REFUND.getValue(),
            QUANTUM_CARD_NOT_ACTIVE_FEE.getValue(),
            CHILD_ACCOUNT_OPEN_CARD.getValue(),
            QUANTUM_CARD_AUTHORIZATION_FEE.getValue(),
            QUANTUM_CARD_ATM_FEE.getValue(),
            QUANTUM_CARD_APPLE_PAY_FEE.getValue(),
            QUANTUM_CARD_MAKE_CARD_FEE.getValue(),
            FREE_OPEN_CARD.getValue(),
            DECLINE_FEE_MERCHANT_ACCOUNT.getValue(),
            DECLINE_FEE_API_ACCOUNT.getValue()
    );


    public static final List<AccountFeeType> PERCENT_FEES = List.of(
            CRYPTO_TRANSFER_RATE,
            CRYPTO_ASSET_PAYOUT_MARKUP,
            CryptoExchange,
            CryptoExchangeFeeL1,
            CryptoExchangeFeeL2,
            CryptoExchangeFeeL3,
            TransferOutCrossChain,
            CryptoExchangeLegalTenderRate,
            CryptoExchangeStableRate,
            CryptoExchangeMainStreamRate,
            CryptoUSDInbound,
            CryptoUSDCInbound,
            CryptoDepositFeeFromQuantumAccount,
            BaseCurrencyRateAdd,
            CNHMarkupFee,
            GlobalAccountExceedThresholdRate,
            GlobalAccountTransferFee,
            GlobalAccountToQbitWallet,
            PaymentServiceOffers,
            SettleRate,
            GlobalInboundCurrencyMain_CC,
            GlobalInboundCurrencyMain_CL,
            GlobalInboundCurrencyMain_PY,
            GlobalInboundCurrencyMain_L2,
            GlobalInboundCurrencyMain_HF,
            GlobalInboundCurrencyOther_CC,
            GlobalInboundCurrencyOther_CL,
            GlobalInboundCurrencyOther_PY,
            GlobalInboundCurrencyOther_L2,
            GlobalInboundCurrencyOther_HF,
            GlobalTransferIn_CC,
            GlobalTransferIn_CL,
            GlobalTransferIn_PY,
            GlobalTransferIn_L2,
            GlobalTransferIn_HF,
            GlobalTransferIn_QB,
            GlobalTransferIn_ZB,
            GlobalTransferIn_RF,
            GlobalTransferIn_EP,
            AccountDeposit,
            AuthorizationMarkupNM,
            AuthorizationMarkupRP,
            AuthorizationMarkupTP,
            AuthorizationMarkupTPNew,
            DeclineFee,
            TransactionRefundFee,
            TransactionRefundFeeRefund
    );


    /**
     * 加密资产费率集合
     */
    public static final List<AccountFeeType> CRYPTO_ASSET_COLLECTION = List.of(
            CryptoTransfer,
            CRYPTO_TRANSFER_RATE,
            CRYPTO_ASSET_PAYOUT_MARKUP,
            CRYPTO_TRANSFER_OUR,
            CRYPTO_TRANSFER_SHA,
            CryptoExchange,
            CryptoExchangeFeeL1,
            CryptoExchangeFeeL2,
            CryptoExchangeFeeL3,
            TransferOutCrossChain,
            CryptoExchangeLegalTenderRate,
            CryptoExchangeStableRate,
            CryptoExchangeMainStreamRate,
            CryptoUSDInbound,
            CryptoUSDCInbound,
            CryptoDepositFeeFromQuantumAccount
    );
    /**
     * 阶梯费率类型
     */
    public static final List<AccountFeeType> TIERED_FEES = List.of(QUANTUM_ACCOUNT_CASH_BACK,
            DECLINE_FEE_MERCHANT_ACCOUNT,
            DECLINE_FEE_API_ACCOUNT,
            QUANTUM_CARD_RP_CONSUMPTION_CASH_BACK,
            QUANTUM_CARD_RC_CONSUMPTION_CASH_BACK,
            QUANTUM_CARD_NM_CONSUMPTION_CASH_BACK,
            QUANTUM_CARD_IPR_AND_RP_CONSUMPTION_CASH_BACK,
            QUANTUM_CARD_IPR_AND_RC_CONSUMPTION_CASH_BACK,
            QUANTUM_CREATE_CARD_CASH_BACK,
            QUANTUM_RP_CREATE_CARD_CASH_BACK,
            QUANTUM_RC_CREATE_CARD_CASH_BACK,
            GLOBAL_ACCOUNT_SETTLE_CASH_BACK,
            GLOBAL_ACCOUNT_OTHER_PAYMENT_CASH_BACK,
            GLOBAL_ACCOUNT_CC_INBOUND_CASH_BACK,
            GLOBAL_ACCOUNT_CL_INBOUND_CASH_BACK,
            GLOBAL_ACCOUNT_PY_INBOUND_CASH_BACK,
            CRYPTO_ASSET_USD_TRADE_CASH_BACK,

            ASSETS_ACCOUNT_TRADING,
            GLOBAL_ACCOUNT_OPEN,
            GLOBAL_ACCOUNT_DEPOSIT,
            QBIT_CARD_ACCOUNT_DEPOSIT,
            CHANNEL_CUSTOMER_ACTIVATION);


    @JsonValue
    private final String value;

    private final boolean tiered;

    AccountFeeType(String value) {
        this.value = value;
        tiered = false;
    }

    AccountFeeType(String value, boolean tiered) {
        this.value = value;
        this.tiered = tiered;
    }

    public static AccountFeeType getByValue(String value) {
        AccountFeeType[] accountFeeTypes = AccountFeeType.values();
        for (AccountFeeType accountFeeType : accountFeeTypes) {
            if (accountFeeType.value.equals(value)) {
                return accountFeeType;
            }
        }
        return null;
    }
}
