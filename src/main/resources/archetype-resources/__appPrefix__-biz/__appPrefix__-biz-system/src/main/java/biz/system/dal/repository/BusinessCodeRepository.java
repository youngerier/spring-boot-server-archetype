#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.repository;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import ${package}.biz.system.dal.entity.BusinessCode;
import ${package}.biz.system.dal.mapper.BusinessCodeMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessCodeRepository extends ServiceImpl<BusinessCodeMapper, BusinessCode> {
}
