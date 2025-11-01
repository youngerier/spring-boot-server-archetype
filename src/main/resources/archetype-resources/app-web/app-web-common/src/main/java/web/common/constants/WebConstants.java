#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.constants;

public class WebConstants {

    public static final String API_V1_PREFIX = "/api/v1";

    public static final String API_V2_PREFIX = "/api/v2";

    public static final String ADMIN_API_V1_PREFIX = "/admin" + API_V1_PREFIX;

    public static final String MEMBER_API_V1_PREFIX = "/member" + API_V1_PREFIX;

    public static final String ADMIN_API_V2_PREFIX = "/admin" + API_V2_PREFIX;

    public static final String COMMON_API_V1_PREFIX = "/common" + API_V1_PREFIX;

}
