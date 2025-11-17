#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.biz.system.dal.entity.ClientFile;
import ${package}.biz.system.dal.mapper.ClientFileMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/14 13:56
 **/
@Repository
public class ClientFileRepository extends ServiceImpl<ClientFileMapper, ClientFile> {
}
