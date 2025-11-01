#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhoubobing
 **/
@Data
@Table("business_code")
public class BusinessCode {
    /**
     * 消息的唯一标识符。
     */
    private Long id;

    /**
     * 与消息关联的代码。
     */
    private String code;

    /**
     * 消息文本。
     */
    private String message;

    /**
     * 消息所使用的语言。
     */
    private String language;

    /**
     * 消息的创建时间。
     */
    private LocalDateTime createTime;

    /**
     * 最后更新消息的时间。
     */
    private LocalDateTime updateTime;

    /**
     * 删除消息的时间。
     */
    private LocalDateTime deleteTime;

    /**
     * 关于消息的附加备注或注释。
     */
    private String remarks;
}
