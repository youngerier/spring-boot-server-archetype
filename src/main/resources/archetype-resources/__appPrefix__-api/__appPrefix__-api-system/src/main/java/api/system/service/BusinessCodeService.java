#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.system.service;


import ${package}.api.system.modal.dto.BusinessCodeDTO;

import java.util.List;

/**
 * 业务状态码
 */
public interface BusinessCodeService {

    List<BusinessCodeDTO> list(String code);
}
