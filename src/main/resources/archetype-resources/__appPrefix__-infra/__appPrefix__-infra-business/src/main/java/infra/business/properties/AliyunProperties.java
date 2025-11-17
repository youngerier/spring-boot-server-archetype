#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {

    private String dmEndpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private SlsProperties sls;


    @Data
    public static class SlsProperties {

        private String endpoint;

        private String accessKeyId;

        private String accessKeySecret;

        private String project;

        private String logStore;
    }
}
