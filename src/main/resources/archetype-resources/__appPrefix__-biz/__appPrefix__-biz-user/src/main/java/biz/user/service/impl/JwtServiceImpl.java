#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.user.service.impl;

import ${package}.api.user.model.dto.AccountUserDTO;
import ${package}.api.user.service.AccountUserService;
import ${package}.infra.business.AssertUtils;
import ${package}.infra.business.JwtService;
import ${package}.infra.business.JwtUser;
import ${package}.infra.business.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static ${package}.infra.business.constants.WebConstants.JWT_AID;
import static ${package}.infra.business.constants.WebConstants.JWT_TID;
import static ${package}.infra.business.constants.WebConstants.JWT_UID;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProperties properties;


    @Override
    public String generateToken(JwtUser username) {

        Long userId = username.getUserId();

        return Jwts.builder()
                .subject(username.getUsername())
                .issuedAt(new Date())
                .claim(JWT_UID, username.getUserId())
                .claim(JWT_TID, username.getTenantId())
                .expiration(DateUtils.addDays(new Date(), properties.getAccessTokenExpirationDays()))
                .signWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                .compact();
    }

    @Override
    public int getAccessTokenExpirationDays() {
        return properties.getAccessTokenExpirationDays();
    }

    @Override
    public int getRefreshTokenExpirationDays() {
        return properties.getRefreshTokenExpirationDays();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * 生成refresh token
     *
     * @param username 用户名
     * @return refresh token字符串
     */
    @Override
    public String generateRefreshToken(JwtUser username) {
        return Jwts.builder()
                .subject(username.getUsername())
                .issuedAt(new Date())
                .expiration(DateUtils.addDays(new Date(), properties.getRefreshTokenExpirationDays()))
                .signWith(Keys.hmacShaKeyFor(properties.getRefreshSecretKey().getBytes()))
                .compact();
    }

    /**
     * 验证refresh token
     *
     * @param token refresh token字符串
     * @return 是否有效
     */
    @Override
    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(properties.getRefreshSecretKey().getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从refresh token中提取用户名
     *
     * @param token refresh token字符串
     * @return 用户名
     */
    @Override
    public JwtUser extractRefreshTokenUsername(String token) {
        String subject = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(properties.getRefreshSecretKey().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return JwtUser.builder()
                .username(subject)
                .tenantId(1L)
                .userId(1L)
                .build();
    }

    /**
     * 从令牌中提取用户信息的通用方法
     *
     * @param token 令牌字符串
     * @return JWT用户对象
     */
    @Override
    public JwtUser extractUser(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                .build();
        Claims payload = null;
        try {
            payload = parser
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("用户请求access_token已过期", e);
            throw new SecurityException("access_token is expired", e);
        }
        
        // 构建attributes，包含所有除了基本字段外的其他属性
        Map<String, Object> attributes = Map.of(
                JWT_AID, Long.parseLong(payload.get(JWT_AID).toString())
        );
        
        return JwtUser.builder()
                .userId(Long.parseLong(payload.get(JWT_UID).toString()))
                .tenantId(Long.parseLong(payload.get(JWT_TID).toString()))
                .username(payload.getSubject())
                .attributes(attributes)
                .build();
    }
}