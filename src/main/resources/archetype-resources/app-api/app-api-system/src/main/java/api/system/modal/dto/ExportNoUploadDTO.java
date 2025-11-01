#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.ByteArrayOutputStream;

/**
 * @author zhoubobing
 * @Description
 * @Date 2023/8/31 12:32
 **/

@AllArgsConstructor
@Data
public class ExportNoUploadDTO {

    private ByteArrayOutputStream stream;


    private String uploadUrl;

    private String fileName;

    public ExportNoUploadDTO(ByteArrayOutputStream stream, String uploadUrl) {
        this.stream = stream;
        this.uploadUrl = uploadUrl;
    }
}
