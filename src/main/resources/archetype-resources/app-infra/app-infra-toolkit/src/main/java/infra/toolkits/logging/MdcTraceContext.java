#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\\' )
package ${package}.infra.toolkits.logging;

import org.slf4j.MDC;

import java.util.Map;
import java.util.Objects;

public final class MdcTraceContext implements AutoCloseable {

    private final Map<String, String> previous;

    private MdcTraceContext(Map<String, String> previous) {
        this.previous = previous;
    }

    public static MdcTraceContext put(String key, String value) {
        Map<String, String> prev = MDC.getCopyOfContextMap();
        if (value == null || value.isEmpty()) {
            MDC.remove(key);
        } else {
            MDC.put(key, value);
        }
        return new MdcTraceContext(prev);
    }

    public static MdcTraceContext setAll(Map<String, String> values) {
        Map<String, String> prev = MDC.getCopyOfContextMap();
        if (values == null || values.isEmpty()) {
            MDC.clear();
        } else {
            MDC.setContextMap(values);
        }
        return new MdcTraceContext(prev);
    }

    @Override
    public void close() {
        if (previous == null || previous.isEmpty()) {
            MDC.clear();
        } else {
            MDC.setContextMap(previous);
        }
    }
}

