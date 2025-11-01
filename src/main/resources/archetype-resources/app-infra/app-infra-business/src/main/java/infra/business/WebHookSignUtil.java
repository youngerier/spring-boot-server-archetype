#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business;

import ${package}.infra.business.json.JSONUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author martinjiang
 */
public class WebHookSignUtil {
    private WebHookSignUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] sign(String str, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Key sk = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance(sk.getAlgorithm());
        mac.init(sk);
        return mac.doFinal(str.getBytes());
    }

    public static String joinStr(Map<String, Object> data) {
        String[] keys = data.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            Object val = data.get(key);
            if (val == null) {
                val = "";
            }
            if (val instanceof Map<?, ?>) {
                val = JSONUtil.toJson(new TreeMap<>((Map<?, ?>) val));
            }
            if (val instanceof Collection<?> || val instanceof Object[]) {
                val = JSONUtil.toJson(val);
            }
            sb.append(key).append("=").append(val).append("&");
        }
        String str = sb.toString();
        return str.substring(0, str.length() - 1);
    }
}