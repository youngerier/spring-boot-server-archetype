#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/14 13:52
 **/
@Data
@Table(value = "client_file")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientFile implements Serializable {

    private static final long serialVersionUID = 2659201570802551358L;
    /**
     * 文件id
     */
    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    /**
     * 文件唯一标识
     */
    private String url;

    /**
     * 文件大小，单位字节
     */
    private Long size;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 原始文件名
     */
    @Column(value = "original_filename")
    private String originalFilename;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @Column(value = "upload_id")
    private String uploadId;

    /**
     * 版本号
     */
    private Integer version;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;
}
