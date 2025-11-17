#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.initializer;

import ${package}.api.system.service.BusinessCodeService;
import ${package}.infra.business.CustomerExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionCodeInitializer implements SystemInitializer {
    @Override
    public boolean requireInitialize() {
        return true;
    }

    @Override
    public void initialize(ApplicationContext context) {
        try {
            BusinessCodeService businessCodeService = context.getBean(BusinessCodeService.class);
            CustomerExceptionFactory.setBusinessCodeService(businessCodeService);
            log.info("Initialized BusinessCodeService for CustomerCodeUtils");
        } catch (Exception e) {
            log.warn("Failed to initialize BusinessCodeService for CustomerCodeUtils", e);
        }
    }
}
