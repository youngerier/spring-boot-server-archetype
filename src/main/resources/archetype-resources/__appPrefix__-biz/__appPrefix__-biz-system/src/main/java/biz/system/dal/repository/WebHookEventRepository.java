#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.biz.system.dal.entity.WebhookEvent;
import ${package}.biz.system.dal.mapper.WebhookEventMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 10:58
 * 数据访问层实现类
 */
@Repository
public class WebHookEventRepository extends ServiceImpl<WebhookEventMapper, WebhookEvent> {

}
