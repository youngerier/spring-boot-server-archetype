#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtUser {

    private Long userId;

    private Long tenantId;

    private String username;

    /**
     * 用户属性
     */
    private Map<String, Object> attributes = Collections.emptyMap();

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    /**
     * 获取属性并判断空
     *
     * @param key 属性名称
     * @return 属性值
     */
    public <T> T requireAttribute(String key) {
        T result = getAttribute(key);
        AssertUtils.notNull(result, () -> String.format("attribute name = %s must not null", key));
        return result;
    }
}
