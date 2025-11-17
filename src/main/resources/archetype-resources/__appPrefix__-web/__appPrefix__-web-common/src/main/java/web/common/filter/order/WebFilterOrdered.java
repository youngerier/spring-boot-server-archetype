#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.filter.order;

import ${package}.core.enums.DescriptiveEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.Ordered;


@AllArgsConstructor
@Getter
public enum WebFilterOrdered implements Ordered, DescriptiveEnum<Integer> {

    TraceFilter(Ordered.HIGHEST_PRECEDENCE, "TraceFilter"),
    ContentCachingRequestFilter(Ordered.HIGHEST_PRECEDENCE + 5, "ContentCachingRequestFilter"),
    ErrorHandlingFilter(TraceFilter.getOrder() + 10, "ErrorHandlingFilter"),
    SignatureValidationFilter(TraceFilter.getOrder() + 20, "SignatureValidationFilter"),
    ApiLoggingFilter(TraceFilter.getOrder() + 40, "ApiLoggingFilter"),
    ;

    private final int order;

    private final String desc;


    @Override
    public Integer getCode() {
        return order;
    }
}
