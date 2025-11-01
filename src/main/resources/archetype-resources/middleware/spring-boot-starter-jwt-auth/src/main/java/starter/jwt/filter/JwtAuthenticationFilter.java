#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.filter;

import com.alibaba.fastjson2.JSON;
import ${package}.infra.business.AppType;
import ${package}.infra.business.JwtService;
import ${package}.infra.business.JwtUser;
import ${package}.infra.business.Result;
import ${package}.infra.toolkits.network.HttpResponseUtil;
import ${package}.starter.jwt.model.TokenResponse;
import ${package}.starter.jwt.provider.authentication.JwtAuthenticationTokenFactory;
import ${package}.starter.jwt.token.JwtAuthenticationToken;
import ${package}.starter.jwt.token.LoginMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String LOGIN_METHOD_VARIABLE_NAME = "loginMethod";

    public static final String APP_TYPE_VARIABLE_NAME = "appType";

    private final JwtService jwtService;
    private final JwtAuthenticationTokenFactory tokenFactory;
    private final RequestMatcher requestMatcher;

    public JwtAuthenticationFilter(JwtService jwtService, JwtAuthenticationTokenFactory tokenFactory) {
        super(buildRequestMatcher());
        this.jwtService = jwtService;
        this.tokenFactory = tokenFactory;
        this.requestMatcher = buildRequestMatcher();
    }

    private static AntPathRequestMatcher buildRequestMatcher() {
        return new AntPathRequestMatcher(String.format("/{%s}/api/v1/auth/login/{%s}",
                APP_TYPE_VARIABLE_NAME, LOGIN_METHOD_VARIABLE_NAME), HttpMethod.POST.name());
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Map<String, String> pathVariables = requestMatcher.matcher(request).getVariables();

        LoginMethod loginMethod = LoginMethod.valueOf(pathVariables.get(LOGIN_METHOD_VARIABLE_NAME));
        AppType appType = AppType.of(pathVariables.get(APP_TYPE_VARIABLE_NAME));
        Map<String, String> requestParams = getRequestParams(request);
        JwtAuthenticationToken unauthenticatedToken = tokenFactory.build(loginMethod, appType, requestParams);
        return this.getAuthenticationManager().authenticate(unauthenticatedToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("login failed: {}", failed.getMessage(), failed);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        HttpResponseUtil.writeJsonText(response, JSON.toJSONString(Result.fail(failed.getMessage())));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        Object token = authentication.getPrincipal();
        if (token instanceof JwtUser loginUser) {
            String jwtToken = jwtService.generateToken(loginUser);
            String refreshToken = jwtService.generateRefreshToken(loginUser);

            // 使用TokenResponse模型类封装token信息
            TokenResponse tokenResponse = TokenResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .expireTime(getDateLong(jwtService.getAccessTokenExpirationDays()))
                    .refreshTokenExpireTime(getDateLong(jwtService.getRefreshTokenExpirationDays()))
                    .build();

            // publish event InteractiveAuthenticationSuccessEvent
            HttpResponseUtil.writeJsonText(response, JSON.toJSONString(Result.ok(tokenResponse)));
        } else {
            throw new BadCredentialsException("Invalid token");
        }
    }

    /**
     * utc 时间
     */
    private Long getDateLong(int days) {
        LocalDateTime time = LocalDateTime.now().plusDays(days);
        // 转换为 UTC 时区的时间戳（毫秒）
        return time
                .atZone(ZoneId.of("UTC"))
                .toInstant()
                .toEpochMilli();
    }

    /**
     * 获取请求参数
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> params = new HashMap<>(8);
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }

        // 如果是 JSON 请求体，解析并合并到参数映射中
        try {
            Map<String, String> jsonParams = getRequestBodyJsonParams(request);
            if (!jsonParams.isEmpty()) {
                // JSON 参数优先级更高，覆盖同名的表单/查询参数
                params.putAll(jsonParams);
            }
        } catch (IOException e) {
            log.warn("Failed to parse JSON request body for params", e);
        }

        return params;
    }

    /**
     * 从 JSON 请求体中解析参数为 Map<String, String>
     */
    private Map<String, String> getRequestBodyJsonParams(HttpServletRequest request) throws IOException {
        String contentType = request.getContentType();
        if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
            return Collections.emptyMap();
        }

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        if (body.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, Object> parsed = JSON.parseObject(body);
        Map<String, String> result = new HashMap<>(Math.max(8, parsed.size()));
        for (Map.Entry<String, Object> entry : parsed.entrySet()) {
            Object value = entry.getValue();
            result.put(entry.getKey(), value == null ? null : String.valueOf(value));
        }
        return result;
    }


}
