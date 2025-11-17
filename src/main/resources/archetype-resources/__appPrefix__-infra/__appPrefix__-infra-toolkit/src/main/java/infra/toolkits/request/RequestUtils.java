#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.request;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liang
 * @description RequestUtils
 * @date 2022/8/5 10:21
 */
public class RequestUtils {


    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes servletAttrs) {
            return servletAttrs.getRequest();
        }
        return null;
    }

    /**
     * 是否英文语言
     *
     * @return true 英文
     */
    public static boolean isEn() {
        HttpServletRequest httpServletRequest = getCurrentRequest();
        String lang = httpServletRequest.getHeader("Lang");
        return "en_US".equalsIgnoreCase(lang);
    }

    /**
     * 获取当前请求的所有headers
     *
     * @return Map<String, String>
     */
    public static Map<String, Object> getHeaders() {
        HttpServletRequest request = getCurrentRequest();
        Map<String, Object> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersMap.put(headerName, headerValue);
        }
        return headersMap;
    }

    /**
     * 获取当前请求的所有参数 ?user=123
     *
     * @return Map<String, String>
     */
    public static Map<String, Object> getQueryParameters() {
        HttpServletRequest request = getCurrentRequest();
        Map<String, Object> queryParamsMap = new HashMap<>();
        String queryString = request.getQueryString();
        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length > 1) {
                    queryParamsMap.put(keyValue[0], keyValue[1]);
                } else {
                    queryParamsMap.put(keyValue[0], "");
                    // 如果参数值为空
                }
            }
        }
        return queryParamsMap;
    }


}
