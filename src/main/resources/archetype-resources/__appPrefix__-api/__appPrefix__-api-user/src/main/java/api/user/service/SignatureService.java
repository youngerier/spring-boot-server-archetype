#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.user.service;

/**
 * 签名服务接口，提供请求签名的验证和生成功能
 * 用于确保API调用的安全性和请求的完整性
 */
public interface SignatureService {
    /**
     * 验证请求签名是否有效
     *
     * @param signStr 用于签名的原始字符串，通常包含请求的关键信息（如时间戳、URI、请求体等）
     * @param sign    客户端提供的签名字符串
     * @param appId   应用标识，用于获取对应的密钥
     */
    void validateSignature(String signStr, String sign, String appId);

    /**
     * 生成请求签名
     *
     * @param data  需要签名的原始数据
     * @param appId 应用标识，用于获取对应的密钥
     * @return 生成的签名字符串
     */
    String generateSignature(String data, String appId);
}