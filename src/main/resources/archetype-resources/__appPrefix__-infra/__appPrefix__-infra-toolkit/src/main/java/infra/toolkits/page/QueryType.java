#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QueryType {

    COUNT_TOTAL("统计总数"),
    QUERY_RECORDS("查询结果集"),
    QUERY_BOTH("查询总数和结果集");

    private final String desc;
}