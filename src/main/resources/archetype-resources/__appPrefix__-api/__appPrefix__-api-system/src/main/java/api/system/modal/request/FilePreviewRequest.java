#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/14 12:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilePreviewRequest {

    String url;

    /**
     * 过期时间，单位秒
     */
    private Long expire;
}
