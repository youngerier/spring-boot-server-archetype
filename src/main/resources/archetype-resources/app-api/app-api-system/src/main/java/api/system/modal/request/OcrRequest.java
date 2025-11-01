#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.request;

import ${package}.core.enums.OcrSide;
import ${package}.core.enums.OcrType;
import lombok.Data;

/**
 * @author martinjiang
 */
@Data
public class OcrRequest {

    private String image;

    private OcrSide side;

    private OcrType type;

    private String accountId;
}
