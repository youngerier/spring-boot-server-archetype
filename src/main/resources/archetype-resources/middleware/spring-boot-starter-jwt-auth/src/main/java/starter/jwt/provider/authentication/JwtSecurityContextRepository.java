#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.provider.authentication;

import ${package}.infra.business.JwtService;
import ${package}.infra.business.JwtUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements SecurityContextRepository {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SECURITY_CONTEXT_KEY = JwtSecurityContextRepository.class.getName() + ".SPRING_SECURITY_CONTEXT";

    private final JwtService jwtService;

    @SuppressWarnings("deprecated")
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return loadSecurityContext(requestResponseHolder.getRequest());
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(SECURITY_CONTEXT_KEY, context);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return request.getAttribute(SECURITY_CONTEXT_KEY) != null;
    }


    private SecurityContext loadSecurityContext(HttpServletRequest request) {
        String jwtToken = extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (!StringUtils.hasText(jwtToken)) {
            return SecurityContextHolder.createEmptyContext();
        }

        try {
            JwtUser user = jwtService.extractUser(jwtToken);
            UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(
                    user, null, Collections.emptyList()
            );
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            return context;
        } catch (ExpiredJwtException | SignatureException e) {
            log.warn("JWT token invalid: {}", e.getMessage());
            return SecurityContextHolder.createEmptyContext();
        } catch (Exception e) {
            log.error("Failed to load security context", e);
            return SecurityContextHolder.createEmptyContext();
        }
    }

    private String extractToken(String header) {
        if (!StringUtils.hasText(header) || !header.startsWith(BEARER_PREFIX)) {
            return "";
        }
        return header.substring(BEARER_PREFIX.length()).trim();
    }
}