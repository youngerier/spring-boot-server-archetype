#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.modal.dto;

import lombok.Data;

import java.util.Date;

/**
 * @description
 * @Date 2025/9/23 16:24
 * @author zhoubobing
 */

@Data
public class BusinessCodeDTO {

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
    private Date createTime;

    /**
     * 最后更新消息的时间。
     */
    private Date updateTime;

    /**
     * 删除消息的时间。
     */
    private Date deleteTime;

    /**
     * 关于消息的附加备注或注释。
     */
    private String remarks;


    public static BusinessCodeDTO paramOf(String code, String message) {
        BusinessCodeDTO one = new BusinessCodeDTO();
        one.code = code;
        one.message = message;
        return one;
    }
}

