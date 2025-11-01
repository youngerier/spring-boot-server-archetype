#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.handler;

import com.alibaba.fastjson2.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * jsonb 转 List<Object>
 *
 * @author klover
 */
@MappedTypes({List.class})
public class ArrayObjectTypeHandler extends BaseTypeHandler<List<Object>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType)
            throws SQLException {
        if (ps != null) {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue(JSON.toJSONString(parameter));
            ps.setObject(i, jsonObject);
        }
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getString(columnName));
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getString(columnIndex));
    }

    @Override
    public List<Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getString(columnIndex));
    }

    private List<Object> getArray(String params) {
        if (params == null) {
            return Collections.emptyList();
        }
        return JSON.parseObject(params, List.class);
    }
}
