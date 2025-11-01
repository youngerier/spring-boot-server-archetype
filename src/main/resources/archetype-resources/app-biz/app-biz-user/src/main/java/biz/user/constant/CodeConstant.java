#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.constant;

/**
 * @description
 * @Date 2025/9/24 14:48
 * @author zhoubobing
 */
public class CodeConstant {
    /**
     * 参数校验失败
     */
    public static final String CODE_400001 = "401001";

    /**
     * 参数校验失败: {0}
     */
    public static final String CODE_400011 = "400011";

    /**
     * 参数格式错误
     */
    public static final String CODE_400002 = "400002";

    /**
     * 不支持的请求方式
     */
    public static final String CODE_400003 = "400003";

    /**
     * 未授权访问
     */
    public static final String CODE_401000 = "401000";

    /**
     * 签名异常
     */
    public static final String CODE_401001 = "401001";

    /**
     * Token 已过期
     */
    public static final String CODE_401002 = "401002";

    /**
     * 拒绝访问（无权限）
     */
    public static final String CODE_403001 = "403001";

    /**
     * 资源不存在
     */
    public static final String CODE_404001 = "404001";

    /**
     * 接口不存在
     */
    public static final String CODE_404002 = "404002";

    /**
     * 请求方法不被支持，请检查接口调用方式
     */
    public static final String CODE_405001 = "405001";

    /**
     * 请求超时
     */
    public static final String CODE_408001 = "408001";

    /**
     * 系统繁忙，请稍后重试
     */
    public static final String CODE_500000 = "500000";

    /**
     * 内部服务调用异常
     */
    public static final String CODE_500001 = "500001";

    /**
     * 数据库执行异常
     */
    public static final String CODE_500002 = "500002";

    /**
     * 外部接口调用失败
     */
    public static final String CODE_502001 = "502001";

    /**
     * 网关不可用
     */
    public static final String CODE_502002 = "502002";

    /**
     * 请求被限流
     */
    public static final String CODE_503001 = "503001";

    /**
     * 服务降级
     */
    public static final String CODE_503002 = "503002";

    /**
     * zh: 网关超时 | en: Gateway Timeout
     */
    public static final String CODE_504000 = "504000";

    /**
     * zh: 上游服务无响应 | en: Upstream service not responding
     */
    public static final String CODE_504001 = "504001";

    /**
     * zh: 第三-party接口超时 | en: Third-party interface timeout
     */
    public static final String CODE_504002 = "504002";

    /**
     * 内部服务调用超时 | en: Internal service call timeout
     */
    public static final String CODE_504003 = "504003";

    /**
     * zh: 数据处理超时 | en: Data processing timeout
     */
    public static final String CODE_504004 = "504004";

    /**
     * 请求重复提交
     */
    public static final String CODE_409001 = "409001";

    /**
     * 业务处理失败
     */
    public static final String CODE_600001 = "600001";

    /**
     * 状态不允许当前操作
     */
    public static final String CODE_600002 = "600002";

    /**
     * 数据校验失败
     */
    public static final String CODE_600003 = "600003";

    /**
     * 渠道返回异常
     */
    public static final String CODE_600004 = "600004";

    /**
     * 风控拦截
     */
    public static final String CODE_600005 = "600005";


    // ================= 账户相关 =================

    /**
     * 账户不存在
     */
    public static final String CODE_620001 = "620001";

    /**
     * 账户已冻结
     */
    public static final String CODE_620002 = "620002";

    /**
     * 账户状态异常
     */
    public static final String CODE_620003 = "620003";

    /**
     * 余额不足
     */
    public static final String CODE_620004 = "620004";

    /**
     * 账户已注销
     */
    public static final String CODE_620005 = "620005";

    /**
     * 邮箱已注册,请重新输入邮箱
     */
    public static final String CODE_620006 = "620006";

    /**
     * 密码错误
     */
    public static final String CODE_620007 = "620007";
    // ================= 订单相关 =================

    /**
     * 订单不存在
     */
    public static final String CODE_630001 = "630001";

    /**
     * 订单已关闭
     */
    public static final String CODE_630002 = "630002";

    /**
     * 订单已过期
     */
    public static final String CODE_630003 = "630003";

    /**
     * 订单状态不允许当前操作
     */
    public static final String CODE_630004 = "630004";

    /**
     * 重复的订单请求
     */
    public static final String CODE_630005 = "630005";


    // ================= 支付相关 =================

    /**
     * 支付处理中
     */
    public static final String CODE_610001 = "610001";

    /**
     * 支付失败
     */
    public static final String CODE_610002 = "610002";

    /**
     * 支付超时
     */
    public static final String CODE_610003 = "610003";

    /**
     * 不支持的支付方式
     */
    public static final String CODE_610004 = "610004";

    /**
     * 支付渠道未开通
     */
    public static final String CODE_610005 = "610005";


    // ================= 渠道相关 =================

    /**
     * 渠道不可用
     */
    public static final String CODE_640001 = "640001";

    /**
     * 渠道不支持当前币种
     */
    public static final String CODE_640002 = "640002";

    /**
     * 渠道限额不足
     */
    public static final String CODE_640003 = "640003";

    /**
     * 渠道返回异常
     */
    public static final String CODE_640004 = "640004";

    /**
     * 渠道接口调用失败
     */
    public static final String CODE_640005 = "640005";


    // ================= 风控相关 =================

    /**
     * 风控拦截
     */
    public static final String CODE_650001 = "650001";

    /**
     * 超过单笔限额
     */
    public static final String CODE_650002 = "650002";

    /**
     * 超过当日限额
     */
    public static final String CODE_650003 = "650003";

    /**
     * 高风险用户
     */
    public static final String CODE_650004 = "650004";

    /**
     * 可疑交易，待人工审核
     */
    public static final String CODE_650005 = "650005";

}
