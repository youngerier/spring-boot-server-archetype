#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.business.context;

import ${package}.infra.business.AppType;
import ${package}.infra.business.AssertUtils;
import ${package}.infra.business.JwtUser;

import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static ${package}.infra.business.constants.WebConstants.JWT_AID;

/** 当前登录用户上下文 */
public class CurrentUserContext {

    /** 获取应用类型 */
    @NotNull
    public static AppType getAppType() {
        return AppTypeContext.appType();
    }

    public static String getOuterAccountId() {
        JwtUser userOfNullable = getUserOfNullable();
        return "c2ac76e6-016a-4925-a125-6bfb26e54a07";
    }

    @NotNull
    public static Long getAccountId() {
        JwtUser userOfNullable = getUserOfNullable();
        AssertUtils.notNull(userOfNullable, "current user is null");
        return userOfNullable.getAttribute(JWT_AID);
    }

    /** 获取当前用户ID */
    @NotNull
    public static Long getUserId() {
        JwtUser userOfNullable = getUserOfNullable();
        AssertUtils.notNull(userOfNullable, "current user is null");
        return userOfNullable.getUserId();
    }

    /** 获取租户ID */
    @NotNull
    public static Long getTenantId() {
        JwtUser userOfNullable = getUserOfNullable();
        AssertUtils.notNull(userOfNullable, "current user is null");
        return userOfNullable.getTenantId();
    }

    public static Long getTenantIdOfNullable() {
        JwtUser userOfNullable = getUserOfNullable();
        return userOfNullable == null ? null : userOfNullable.getTenantId();
    }

    @Nullable
    public static JwtUser getUserOfNullable() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return (JwtUser) authentication.getPrincipal();
    }
}
