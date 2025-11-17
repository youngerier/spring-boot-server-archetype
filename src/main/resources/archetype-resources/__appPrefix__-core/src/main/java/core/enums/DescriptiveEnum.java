#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.core.enums;

/**
 * 通用枚举接口
 * 所有业务枚举类都需要实现该接口
 *
 * @param <T> 编码值类型
 */
public interface DescriptiveEnum<T> {
    /**
     * 根据编码值获取对应枚举
     *
     * @param code      编码值
     * @param enumClass 枚举类
     * @return 对应的枚举实例
     */
    static <E extends Enum<E> & DescriptiveEnum<?>> E valueOfCode(Object code, Class<E> enumClass) {
        if (code == null) {
            return null;
        }
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 获取枚举编码值
     *
     * @return 编码值
     */
    T getCode();

    /**
     * 获取枚举描述
     *
     * @return 描述信息
     */
    String getDesc();
}
