#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.dal.mapper;

import com.mybatisflex.core.BaseMapper;
import ${package}.biz.system.dal.entity.FilePartDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 18:58
 **/
@Mapper
public interface FilePartDetailMapper extends BaseMapper<FilePartDetail> {
}
