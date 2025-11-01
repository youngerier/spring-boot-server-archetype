#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.service;


import java.util.Map;

/**
 * @author martinjiang
 */
public interface OpenApiWebHookService {

    void handleWebhook(Map<String, Object> body);
}
