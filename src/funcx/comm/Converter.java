package funcx.comm;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换器
 * 
 * <p>
 * 将字符串转换为指定类型,可扩展 <br/>
 * Converter.external(clazz, convert)
 * 
 * @author Sylthas
 * 
 */
public class Converter {

    /** 转换器 **/
    private static Map<Class<?>, Convert<?>> map = new HashMap<Class<?>, Convert<?>>();

    private Converter() {
    }

    /**
     * 类型转换
     * 
     * @param s 字符串
     * @param type 类型
     * @return
     */
    public static Object parse(String s, Class<?> type) {
        return map.get(type).convert(s);
    }

    /**
     * 扩展转换器
     * 
     * @param clazz  类型
     * @param convert 转换实现
     */
    public void external(Class<?> clazz, Convert<?> convert) {
        if (clazz == null)
            throw new NullPointerException("Class is null.");
        if (convert == null)
            throw new NullPointerException("Converter is null.");
        if (map.containsKey(clazz)) {
            throw new RuntimeException(
                    "Cannot replace the exist converter for type '"
                            + clazz.getName() + "'.");
        }
        map.put(clazz, convert);
    }

    /** 转换接口 **/
    interface Convert<T> {
        T convert(String s);
    }

    /** 初始化转换器 **/
    static {
        Convert<?> c = null;
        c = new Convert<Boolean>() {
            public Boolean convert(String s) {
                return Boolean.parseBoolean(s);
            }
        };
        map.put(boolean.class, c);
        map.put(Boolean.class, c);

        c = new Convert<Character>() {
            public Character convert(String s) {
                if (s.length() == 0)
                    throw new IllegalArgumentException(
                            "Cannot convert empty string to char");
                return s.charAt(0);
            }
        };
        map.put(char.class, c);
        map.put(Character.class, c);

        c = new Convert<Byte>() {
            public Byte convert(String s) {
                return Byte.parseByte(s);
            }
        };
        map.put(byte.class, c);
        map.put(Byte.class, c);

        c = new Convert<Double>() {
            public Double convert(String s) {
                return Double.parseDouble(s);
            }
        };
        map.put(double.class, c);
        map.put(Double.class, c);

        c = new Convert<Float>() {
            public Float convert(String s) {
                return Float.parseFloat(s);
            }
        };
        map.put(float.class, c);
        map.put(Float.class, c);

        c = new Convert<Integer>() {
            public Integer convert(String s) {
                return Integer.parseInt(s);
            }
        };
        map.put(int.class, c);
        map.put(Integer.class, c);

        c = new Convert<Long>() {
            public Long convert(String s) {
                return Long.parseLong(s);
            }
        };
        map.put(long.class, c);
        map.put(Long.class, c);

        c = new Convert<Short>() {
            public Short convert(String s) {
                return Short.parseShort(s);
            }
        };
        map.put(short.class, c);
        map.put(Short.class, c);
    }
}
