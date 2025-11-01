#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.request;

import ${package}.api.system.modal.enums.StorageOssFolderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/14 10:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileRequest {

    /**
     * 文件夹
     */
    private StorageOssFolderEnum folder = StorageOssFolderEnum.GENERAL;

    private Integer imageWidthSize = 1000;
    /**
     * 图片高宽比
     */
    private Integer imageHeightSize = 1000;
    /**
     * 缩略图宽高比
     */
    private Integer thumbnailWidthSize = 200;
    /**
     * 缩略图高宽比
     */
    private Integer thumbnailHighSize = 200;
    /**
     * 租户id, ⚠️⚠️⚠️ 异步调用必传 ⚠️️⚠️⚠️
     */
    private String tenantId;
}
