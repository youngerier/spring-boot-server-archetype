#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import ${package}.infra.business.AppType;
import ${package}.infra.business.context.AppTypeContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class MemberApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        AppTypeContext.setAppType(AppType.MEMBER);
        SpringApplication.run(MemberApplication.class, args);
    }

}
