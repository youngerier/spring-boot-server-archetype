#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.mapstruct;

import ${package}.api.system.modal.dto.BusinessCodeDTO;
import ${package}.biz.system.dal.entity.BusinessCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @description
 * @Date 2025/9/23 17:06
 * @author zhoubobing
 */
@Mapper
public interface BusinessCodeConvertor {

    BusinessCodeConvertor INSTANCE = Mappers.getMapper(BusinessCodeConvertor.class);

    default LocalDateTime map(Long timestamp) {
        return timestamp == null ? null :
                Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default Long map(LocalDateTime localDateTime) {
        return localDateTime == null ? null :
                localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    BusinessCodeDTO toDto(BusinessCode businessCode);
}
