#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.handler;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.MappedTypes;

import java.util.Map;

/**
 * jsonb 转 List<TagBO>
 *
 * @author martinjiang
 */
@MappedTypes({Map.class})
public class MapTypeHandler extends AbstractJsonbTypeHandler<Map<String, Object>> {
    @Override
    protected Map<String, Object> getObject(String params) {
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        try {
            return JSON.parseObject(params, Map.class);
        } catch (Exception ignore) {

        }
        return null;
    }
}
