#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.system.constant;

/**
 * @Author: jiang
 * @Site:
 * @Date: 2025/10/13 11:03
 **/
public interface TenantConfigConstant {

    interface TenantProfileSettingKey {
        /**
         * 品牌名称
         */
        String BRAND_NAME = "BRAND_NAME";
        /**
         * 品牌logo
         */
        String BRAND_LOGO = "BRAND_LOGO";
        /**
         * 品牌颜色
         */
        String BRAND_COLOR = "BRAND_COLOR";
        /**
         * 品牌描述
         */
        String BRAND_DESC = "BRAND_DESC";
    }
}
