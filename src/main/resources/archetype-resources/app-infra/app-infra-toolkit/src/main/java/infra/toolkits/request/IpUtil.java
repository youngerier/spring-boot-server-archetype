#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liang
 * @description IpUtil
 * @date 2023/4/20 10:14
 */
@Slf4j
@UtilityClass
public class IpUtil {

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error(e.getMessage());
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") >= 1) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


    public static boolean isValidIp(String ip) {
        if (ip == null) {
            return false;
        }
        // 去除前后空格
        ip = ip.trim();
        // 判断是否是 IPv4 地址
        if (isIpv4(ip)) {
            return true;
        }
        // 判断是否是 IPv6 地址
        return isIpv6(ip);
    }

    public static boolean isIpv4(String ip) {
        String[] parts = ip.split("${symbol_escape}${symbol_escape}.");
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            // 检查是否为空
            if (part.isEmpty()) {
                return false;
            }
            // 检查是否有前导零
            if (part.length() > 1 && part.startsWith("0")) {
                return false;
            }
            // 尝试转换为整数并检查范围
            try {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }


    public static boolean isIpv6(String ip) {
        String[] parts = ip.split(":");
        if (parts.length != 8) {
            return false;
        }
        for (String part : parts) {
            if (part.isEmpty() || part.length() > 4) {
                return false;
            }
            try {
                Integer.parseInt(part, 16);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
