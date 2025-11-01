#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business;

public interface JwtService {
    String generateToken(JwtUser username);

    boolean validateToken(String token);

    String extractUsername(String token);

    String generateRefreshToken(JwtUser username);

    boolean validateRefreshToken(String token);

    JwtUser extractRefreshTokenUsername(String token);

    JwtUser extractUser(String token);

    int getAccessTokenExpirationDays();

    int getRefreshTokenExpirationDays();
}
