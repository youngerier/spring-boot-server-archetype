#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/14 10:06
 **/
@Getter
@AllArgsConstructor
public enum StorageOssFolderEnum {
    GENERAL("general");
    private final String value;
}
