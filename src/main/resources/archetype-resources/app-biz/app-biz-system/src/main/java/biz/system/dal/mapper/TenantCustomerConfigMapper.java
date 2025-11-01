#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.mapper;

import com.mybatisflex.core.BaseMapper;
import ${package}.biz.system.dal.entity.TenantCustomerConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jiang
 *   @Site:
 *   @Date: 2025/10/13 10:43
 *   租户配置表
 * 数据访问层Mapper接口
 */
public interface TenantCustomerConfigMapper extends BaseMapper<TenantCustomerConfig> {
}
