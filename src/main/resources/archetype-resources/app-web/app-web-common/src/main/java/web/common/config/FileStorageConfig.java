#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.config;

import org.dromara.x.file.storage.spring.FileStorageAutoConfiguration;
import org.dromara.x.file.storage.spring.SpringFileStorageProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:38
 **/
@Configuration
@Import({FileStorageAutoConfiguration.class, SpringFileStorageProperties.class})
public class FileStorageConfig {

}
