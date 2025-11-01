#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.mapper;

import com.mybatisflex.core.BaseMapper;
import ${package}.biz.system.dal.entity.WebhookEvent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author martinjiang
 */
public interface WebhookEventMapper extends BaseMapper<WebhookEvent> {
}
