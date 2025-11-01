#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.biz.system.dal.entity.FileDetail;
import ${package}.biz.system.dal.mapper.FileDetailMapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 19:00
 **/
@Repository
public class FileDetailRepository extends ServiceImpl<FileDetailMapper, FileDetail> {
}
