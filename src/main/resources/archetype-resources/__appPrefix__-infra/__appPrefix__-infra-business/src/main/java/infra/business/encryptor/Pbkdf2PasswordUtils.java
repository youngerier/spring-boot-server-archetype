#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.encryptor;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author martinjiang
 */
public class Pbkdf2PasswordUtils {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    /**
     * 生成密码哈希
     *
     * @param rawPassword 明文密码
     * @param tenantId    租户ID（作为盐）
     * @return Base64 编码的哈希值
     */
    public static String encode(String rawPassword, Long tenantId) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    rawPassword.toCharArray(),
                    tenantId.toString().getBytes(),
                    ITERATIONS,
                    KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("PBKDF2 编码失败", e);
        }
    }

    /**
     * 校验密码
     *
     * @param rawPassword     用户输入的明文密码
     * @param tenantId        租户ID（作为盐）
     * @param encodedPassword 数据库中保存的哈希值
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, Long tenantId, String encodedPassword) {
        String newHash = encode(rawPassword, tenantId);
        return newHash.equals(encodedPassword);
    }

}
