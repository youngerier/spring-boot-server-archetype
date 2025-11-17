#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:07
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileBaseDTO {
    private String fileName;
    private String originalFileName;
    private String fileType;
    private Long fileSize;
    private String fileUrl;
    private Long userId;
}
