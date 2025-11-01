#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 18:52
 **/
@Data
@Table("file_part_detail")
public class FilePartDetail implements Serializable {

    private static final long serialVersionUID = 3603468918182178956L;

    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private String id;

    /**
     * 存储平台
     */
    @Column(value = "platform")
    private String platform;

    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @Column(value = "upload_id")
    private String uploadId;

    /**
     * 分片 ETag
     */
    @Column(value = "e_tag")
    private String eTag;

    /**
     * 分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000
     */
    @Column(value = "part_number")
    private Integer partNumber;

    /**
     * 文件大小，单位字节
     */
    @Column(value = "part_size")
    private Long partSize;

    /**
     * 哈希信息
     */
    @Column(value = "hash_info")
    private String hashInfo;

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


    public static final String COL_ID = "id";

    public static final String COL_PLATFORM = "platform";

    public static final String COL_UPLOAD_ID = "upload_id";

    public static final String COL_E_TAG = "e_tag";

    public static final String COL_PART_NUMBER = "part_number";

    public static final String COL_PART_SIZE = "part_size";

    public static final String COL_HASH_INFO = "hash_info";

    public static final String COL_CREATE_TIME = "create_time";
}
