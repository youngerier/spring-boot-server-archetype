#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.service.impl;

import ${package}.api.user.service.SignatureService;
import ${package}.infra.toolkits.exception.SystemException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class HmacSignatureService implements SignatureService {
    private final Map<String, String> appSecrets = new HashMap<>();
    @PostConstruct
    public void init() {
        // TODO: 从配置中心或数据库加载
        appSecrets.put("test_app_id", "test_secret_key");
    }

    @Override
    public void validateSignature(String signStr, String sign, String appId) {
        String calculatedSign = generateSignature(signStr, appId);
        if (!sign.equals(calculatedSign)) {
            throw new SystemException("签名验证失败");
        }
    }

    @Override
    public String generateSignature(String data, String appId) {
        String secretKey = getSecretKey(appId);
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new SystemException("签名计算失败", e);
        }
    }

    private String getSecretKey(String appId) {
        return Optional.ofNullable(appSecrets.get(appId))
                .orElseThrow(() -> new SystemException("无效的AppID"));
    }
}