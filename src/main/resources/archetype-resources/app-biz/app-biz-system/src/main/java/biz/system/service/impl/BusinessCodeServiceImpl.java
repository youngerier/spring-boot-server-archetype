#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import ${package}.api.system.modal.dto.BusinessCodeDTO;
import ${package}.api.system.service.BusinessCodeService;
import ${package}.biz.system.dal.entity.BusinessCode;
import ${package}.biz.system.dal.repository.BusinessCodeRepository;
import ${package}.biz.system.mapstruct.BusinessCodeConvertor;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusinessCodeServiceImpl implements BusinessCodeService {
    @Resource
    private BusinessCodeRepository businessCodeRepository;

    @Override
    public List<BusinessCodeDTO> list(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(BusinessCode::getCode, code);
        List<BusinessCode> businessCodes = businessCodeRepository.list(queryWrapper);
        return businessCodes.stream().map(BusinessCodeConvertor.INSTANCE::toDto).collect(Collectors.toList());
    }
}
