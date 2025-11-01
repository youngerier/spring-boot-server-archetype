# Spring Boot JWT Authentication Starter

This is a Spring Boot starter that provides JWT-based authentication and authorization functionality.

## Features

- JWT token generation and validation
- Refresh token support
- Configurable security settings
- RBAC (Role-Based Access Control) support
- Custom authentication providers
- Spring Security integration
- Auto-configuration support

## Getting Started

### 1. Add Dependency

Add the following dependency to your `pom.xml`:

```xml

<dependency>
    <groupId>${groupId}</groupId>
    <artifactId>spring-boot-starter-jwt-auth</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Configuration

Configure JWT settings in your `application.yml` or `application.properties`:

```yaml
jwt:
  auth:
    enabled: true
    secret-key: "your-secret-key-here"
    refresh-secret-key: "your-refresh-secret-key-here"
    access-token-expiration-days: 10
    refresh-token-expiration-days: 30
    public-paths:
      - "/actuator/**"
      - "/error"
      - "/swagger-ui/**"
      - "/v3/api-docs/**"
    auth-paths:
      - "/auth/login/**"
      - "/auth/token/**"
    authenticated-only-paths:
      - "/api/v1/member/rbac/my-roles"
      - "/api/v1/member/rbac/my-permissions"
```

### 3. Implement Custom Components

#### Authentication Provider

Create a custom authentication provider that implements user credential validation:

```java

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        // Validate user credentials
        UserDTO user = userService.queryUserByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return new JwtAuthenticationToken(username, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```

#### Authorization Manager

Create a custom authorization manager for RBAC:

```java

@Component
public class RbacAuthorizationManager implements BaseAuthorizationManager {

    private final UserPermissionService userPermissionService;
    private final JwtAuthProperties properties;

    @Override
    public boolean hasPermission(Authentication authentication, HttpServletRequest request,
                                 String requestPath, String httpMethod) {
        // Implement your RBAC logic here
        return userPermissionService.hasResourcePermission(
                authentication.getName(), requestPath, httpMethod);
    }
}
```

### 4. Login Endpoints

The starter automatically provides login endpoints at:

- `POST /{appType}/api/v1/auth/login/PASSWORD` - Password-based login
- `POST /{appType}/api/v1/auth/login/MOBILE_PHONE` - Mobile phone login (future)
- `POST /{appType}/api/v1/auth/login/EMAIL` - Email login (future)

### 5. Token Refresh

Token refresh endpoint:

- `POST /auth/token/refresh` - Refresh access token using refresh token

## Architecture

### Core Components

1. **JwtService** - JWT token generation and validation
2. **JwtAuthenticationFilter** - Handles login requests
3. **JwtSecurityContextRepository** - Manages security context from JWT
4. **JwtAuthenticationToken** - Custom authentication token
5. **BaseAuthorizationManager** - Interface for custom authorization logic

### Auto-Configuration

The starter provides auto-configuration classes:

- `JwtAuthAutoConfiguration` - Configures JWT services and components
- `JwtSecurityAutoConfiguration` - Configures Spring Security integration

### Customization

You can override default configurations by providing your own beans:

```java

@Configuration
public class CustomJwtConfig {

    @Bean
    @Primary
    public JwtService customJwtService(JwtAuthProperties properties) {
        return new CustomJwtService(properties);
    }

    @Bean
    @Primary
    public BaseAuthorizationManager customAuthorizationManager() {
        return new CustomAuthorizationManager();
    }
}
```

## Configuration Properties

| Property                                 | Default        | Description                             |
|------------------------------------------|----------------|-----------------------------------------|
| `jwt.auth.enabled`                       | `true`         | Enable/disable JWT authentication       |
| `jwt.auth.secret-key`                    | Auto-generated | Secret key for access tokens            |
| `jwt.auth.refresh-secret-key`            | Auto-generated | Secret key for refresh tokens           |
| `jwt.auth.access-token-expiration-days`  | `10`           | Access token expiration in days         |
| `jwt.auth.refresh-token-expiration-days` | `30`           | Refresh token expiration in days        |
| `jwt.auth.public-paths`                  | See defaults   | Paths that don't require authentication |
| `jwt.auth.auth-paths`                    | See defaults   | Authentication-related paths            |
| `jwt.auth.authenticated-only-paths`      | See defaults   | Paths requiring only authentication     |

## Security Considerations

1. **Secret Keys**: Use strong, unique secret keys in production
2. **HTTPS**: Always use HTTPS in production
3. **Token Storage**: Store refresh tokens securely on the client side
4. **Expiration**: Use appropriate token expiration times
5. **Validation**: Implement proper input validation in authentication providers

## Examples

See the `ping-web-common` module for a complete example of how to integrate this starter.

## License

This project is licensed under the Apache License 2.0.