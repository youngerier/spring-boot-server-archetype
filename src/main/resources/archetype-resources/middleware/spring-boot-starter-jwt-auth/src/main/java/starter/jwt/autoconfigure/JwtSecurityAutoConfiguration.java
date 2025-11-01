#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.starter.jwt.autoconfigure;

import ${package}.core.enums.LoginUserServcie;
import ${package}.core.enums.VerificationServcie;
import ${package}.infra.business.JwtService;
import ${package}.starter.jwt.filter.JwtAuthenticationFilter;
import ${package}.starter.jwt.properties.AuthSecurityProperties;
import ${package}.starter.jwt.provider.authentication.CustomAuthenticationFailureHandler;
import ${package}.starter.jwt.provider.authentication.CustomAuthenticationProvider;
import ${package}.starter.jwt.provider.authentication.JwtAuthenticationTokenFactory;
import ${package}.starter.jwt.provider.authentication.JwtSecurityContextRepository;
import ${package}.starter.jwt.provider.authorization.BaseAuthorizationManager;
import ${package}.starter.jwt.provider.authorization.DefaultAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Optional;

/**
 * JWT Security Auto Configuration
 * @author wangping
 */
@Configuration
@EnableWebSecurity
@AutoConfiguration
@EnableConfigurationProperties(AuthSecurityProperties.class)
@ConditionalOnProperty(prefix = "auth.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class JwtSecurityAutoConfiguration {

    private final AuthSecurityProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationTokenFactory jwtAuthenticationTokenFactory() {
        return new JwtAuthenticationTokenFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtSecurityContextRepository jwtSecurityContextRepository(JwtService jwtService) {
        return new JwtSecurityContextRepository(jwtService);
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomAuthenticationProvider customAuthenticationProvider(LoginUserServcie loginUserService,
                                                                     PasswordEncoder passwordEncoder,
                                                                     VerificationServcie verificationService) {
        return new CustomAuthenticationProvider(loginUserService, passwordEncoder, verificationService);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, JwtAuthenticationTokenFactory tokenFactory,
                                                           AuthenticationManager authenticationManager) {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, tokenFactory);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    @ConditionalOnMissingBean(BaseAuthorizationManager.class)
    public DefaultAuthorizationManager defaultAuthorizationManager() {
        return new DefaultAuthorizationManager(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
//     @ConditionalOnMissingBean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许凭证
        config.setAllowCredentials(true);
        // 使用 allowedOriginPatterns 代替 allowedOrigins
        config.addAllowedOriginPattern("*");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许的方法
        config.addAllowedMethod("*");
        // 暴露的响应头
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtSecurityContextRepository jwtSecurityContextRepository,
            CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
            BaseAuthorizationManager authorizationManager,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .securityContext(securityContext ->
                        securityContext
                                .securityContextRepository(jwtSecurityContextRepository)
                                .requireExplicitSave(true)
                )
                .authorizeHttpRequests(auth -> {
                    // 忽略所有 options 请求
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    // Configure public paths
                    auth.requestMatchers(Optional.ofNullable(properties.getPublicPaths()).orElse(new String[0])).permitAll();
                    // Use custom authorization manager for all other requests
                    auth.anyRequest().access(authorizationManager);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception ->
                        exception
                                .accessDeniedHandler(customAuthenticationFailureHandler)
                                .authenticationEntryPoint(customAuthenticationFailureHandler)
                )
                .build();
    }
}