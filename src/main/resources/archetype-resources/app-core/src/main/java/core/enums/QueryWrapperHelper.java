#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

import com.mybatisflex.core.query.QueryWrapper;
import ${package}.infra.toolkits.page.AbstractPageQuery;
import ${package}.infra.toolkits.page.QueryOrderType;

import java.util.Objects;

public class QueryWrapperHelper {

    public static QueryWrapper withOrder(AbstractPageQuery<?> query) {
        if (query.requireOrderBy()) {
            QueryWrapper result = QueryWrapper.create();
            for (int i = 0; i < query.getOrderFields().length; i++) {
                String orderField = query.getOrderFields()[i].getOrderField();
                result.orderBy(orderField, Objects.equals(query.getOrderTypes()[i], QueryOrderType.ASC));
            }
            return result;
        }
        return QueryWrapper.create();
    }

}