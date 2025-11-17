#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.network;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public class HttpResponseUtil {

    private HttpResponseUtil() {
    }


    public static void writeJsonText(HttpServletResponse response, String data) {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try {
            response.getWriter().write(data);
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("writeJsonText error", e);
        }
    }
}
