#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.properties;

import ${package}.infra.business.AppType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
//@RefreshScope
@ConfigurationProperties(prefix = "app.config")
public class BusinessProperties {

    private String test;

    private AppType appType;
}
