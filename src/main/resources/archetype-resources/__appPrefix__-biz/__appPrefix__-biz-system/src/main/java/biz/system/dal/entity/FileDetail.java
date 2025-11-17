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
 * @Date: 2025/10/13 18:33
 **/
@Data
@Table(value = "file_detail")
public class FileDetail implements Serializable {

    private static final long serialVersionUID = 3603468918182178956L;

    /**
     * 文件id
     */
    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private Long id;

    /**
     * 文件访问地址
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
     * 基础存储路径
     */
    @Column(value = "base_path")
    private String basePath;

    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * MIME类型
     */
    @Column(value = "content_type")
    private String contentType;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 缩略图访问路径
     */
    @Column(value = "th_url")
    private String thUrl;

    /**
     * 缩略图名称
     */
    @Column(value = "th_filename")
    private String thFilename;

    /**
     * 缩略图大小，单位字节
     */
    @Column(value = "th_size")
    private Long thSize;

    /**
     * 缩略图MIME类型
     */
    @Column(value = "th_content_type")
    private String thContentType;

    /**
     * 文件所属对象id
     */
    @Column(value = "object_id")
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @Column(value = "object_type")
    private String objectType;

    /**
     * 文件元数据
     */
    @Column(value = "metadata")
    private String metadata;

    /**
     * 文件用户元数据
     */
    @Column(value = "user_metadata")
    private String userMetadata;

    /**
     * 缩略图元数据
     */
    @Column(value = "th_metadata")
    private String thMetadata;

    /**
     * 缩略图用户元数据
     */
    @Column(value = "th_user_metadata")
    private String thUserMetadata;

    /**
     * 附加属性
     */
    @Column(value = "attr")
    private String attr;

    /**
     * 哈希信息
     */
    @Column(value = "hash_info")
    private String hashInfo;

    /**
     * 上传ID，仅在手动分片上传时使用
     */
    @Column(value = "upload_id")
    private String uploadId;

    /**
     * 上传状态，仅在手动分片上传时使用，1：初始化完成，2：上传完成
     */
    @Column(value = "upload_status")
    private Integer uploadStatus;

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

    public static final String COL_URL = "url";

    public static final String COL_SIZE = "size";

    public static final String COL_FILENAME = "filename";

    public static final String COL_ORIGINAL_FILENAME = "original_filename";

    public static final String COL_BASE_PATH = "base_path";

    public static final String COL_PATH = "path";

    public static final String COL_EXT = "ext";

    public static final String COL_CONTENT_TYPE = "content_type";

    public static final String COL_PLATFORM = "platform";

    public static final String COL_TH_URL = "th_url";

    public static final String COL_TH_FILENAME = "th_filename";

    public static final String COL_TH_SIZE = "th_size";

    public static final String COL_TH_CONTENT_TYPE = "th_content_type";

    public static final String COL_OBJECT_ID = "object_id";

    public static final String COL_OBJECT_TYPE = "object_type";

    public static final String COL_METADATA = "metadata";

    public static final String COL_USER_METADATA = "user_metadata";

    public static final String COL_TH_METADATA = "th_metadata";

    public static final String COL_TH_USER_METADATA = "th_user_metadata";

    public static final String COL_ATTR = "attr";

    public static final String COL_HASH_INFO = "hash_info";

    public static final String COL_UPLOAD_ID = "upload_id";

    public static final String COL_UPLOAD_STATUS = "upload_status";

    public static final String COL_CREATE_TIME = "create_time";
}
