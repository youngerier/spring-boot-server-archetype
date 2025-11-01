#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import ${package}.infra.business.handler.MapTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Webhook 事件记录表
 * 用于保存外部回调的原始事件数据，并保证幂等。
 */
@Data
@Table("webhook_event")
public class WebhookEvent {

    /** 主键 */
    @Id(keyType = KeyType.Generator, value = "snowFlakeId")
    private Long id;

    /** Webhook API 版本 */
    private String apiVersion;

    /** 响应码 */
    private String code;

    /** Webhook 创建时间（原始时间戳字符串） */
    private String eventTime;

    /** 事件类型，如 KYC.UPDATE */
    private String eventType;

    /** Webhook 唯一事件 ID（幂等用） */
    @Column(value = "event_id")
    private String eventId;

    /** 事件消息 */
    private String message;

    /** 资源 JSON 内容 */
    @Column(typeHandler = MapTypeHandler.class)
    private String resource;

    /** 状态（PENDING, SUCCESS, FAILED） */
    private String status;

    /** 版本号 */
    private Integer version;

    /** 备注 */
    private String remarks;

    /** 数据创建时间 */
    private LocalDateTime createTime;

    /** 数据更新时间 */
    private LocalDateTime updateTime;

    /** 数据删除时间 */
    private LocalDateTime deleteTime;
}
