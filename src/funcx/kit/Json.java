package funcx.kit;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 简易JSON辅助类
 * 
 * <p>
 * 重复造轮子，人人各不同。<br/>
 * 将对象转换为JSON字符串.
 * 
 * @version 1.0
 * @author Sylthas
 * 
 */
public class Json {

    static char separator = '\"';
    
    static String timestampPattern = "yyyy-MM-dd HH:mm:ss";
    
    static String datePattern = "yyyy-MM-dd";

    private StringBuilder bd = null;

    private Json() {
    }

    private Json(StringBuilder bd) {
        this.bd = bd;
    }

    @Override
    public String toString() {
        return bd.toString();
    }

    /** 转换入口 **/
    void render(Object obj) {
        if (obj == null) {
            bd.append("null");
        } else if (obj instanceof String) {
            render(obj.toString()); // 字符串
        } else if (TYPE_MAPPING.contains(obj.getClass())) {
            bd.append(obj); // 基本数据类型和其包装类
        } else if (obj instanceof Date) {
            render((Date) obj); // 日期
        } else {
            if (obj instanceof Map)
                render((Map<?, ?>) obj); // Map
            else if (obj instanceof Collection)
                render((Collection<?>) obj); // 集合
            else if (obj.getClass().isArray())
                renderArr(obj); // 数组
            else
                renderPojo(obj); // JavaBean
        }
    }

    /** 转换字符串 **/
    void render(String s) {
        bd.append(separator);
        escape(s);
        bd.append(separator);
    }

    /** 日期转换 **/
    void render(Date date) {
        if (date instanceof java.sql.Timestamp)
            render(new SimpleDateFormat(timestampPattern).format(date));
        else if (date instanceof java.sql.Time)
            render(date.toString());
        else
            render(new SimpleDateFormat(datePattern).format(date));
    }

    /** 转换Map **/
    void render(Map<?, ?> map) {
        Set<?> keys = map.keySet();
        bd.append('{');
        for (Object key : keys) {
            escape(key.toString());
            bd.append(':');
            render(map.get(key));
            bd.append(',');
        }
        bd.deleteCharAt(bd.length() - 1);
        bd.append('}');
    }

    /** 转换集合 **/
    void render(Collection<?> iterable) {
        bd.append('[');
        for (Iterator<?> it = iterable.iterator(); it.hasNext();) {
            render(it.next());
            if (it.hasNext())
                bd.append(',');
            else
                break;
        }
        bd.append(']');
    }

    /** 转换数组 **/
    void renderArr(Object obj) {
        bd.append('[');
        int len = Array.getLength(obj) - 1;
        if (len > -1) {
            int i = 0;
            for (; i < len; i++) {
                render(Array.get(obj, i));
                bd.append(',');
            }
            render(Array.get(obj, i));
        }
        bd.append(']');
    }

    /** 转换JavaBean **/
    void renderPojo(Object pojo) {
        bd.append('{');
        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field field : fields) {
            escape((field.getName()));
            bd.append(':');
            try {
                field.setAccessible(true);
                render(field.get(pojo));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            bd.append(',');
        }
        bd.deleteCharAt(bd.length() - 1);
        bd.append('}');
    }

    /** Escape 字符串 **/
    void escape(String s) {
        if (s == null) {
            bd.append("null");
        } else {
            char[] cs = s.toCharArray();
            for (char c : cs) {
                switch (c) {
                case '"':
                    bd.append("\\\"");
                    break;
                case '\n':
                    bd.append("\\n");
                    break;
                case '\t':
                    bd.append("\\t");
                    break;
                case '\r':
                    bd.append("\\r");
                    break;
                case '\\':
                    bd.append("\\\\");
                    break;
                default:
                    if ((c >= '\u0000' && c <= '\u001f')
                            || (c >= '\u007f' && c <= '\u009f')
                            || (c >= '\u2000' && c <= '\u20ff')) {
                        String str = Integer.toHexString(c);
                        bd.append("\\u");
                        for (int k = 0; k < 4 - str.length(); k++) {
                            bd.append('0');
                        }
                        bd.append(str);
                    }
                    bd.append(c);
                }
            }
        }
    }

    /** 对外方法 **/
    public static String toJson(Object obj) {
        StringBuilder bd = new StringBuilder();
        Json json = new Json(bd);
        json.render(obj);
        return json.toString();
    }

    /** 设置时间格式 **/
    public static void setTimestampPattern(String timestampPattern) {
        Json.timestampPattern = timestampPattern;
    }

    /** 设置日期格式 **/
    public static void setDatePattern(String datePattern) {
        Json.datePattern = datePattern;
    }


    /** 基本数据类型和其包装类 **/
    private static final Set<Class<?>> TYPE_MAPPING = new HashSet<Class<?>>();
    static {
        TYPE_MAPPING.add(Integer.class);
        TYPE_MAPPING.add(int.class);
        TYPE_MAPPING.add(Double.class);
        TYPE_MAPPING.add(double.class);
        TYPE_MAPPING.add(Float.class);
        TYPE_MAPPING.add(float.class);
        TYPE_MAPPING.add(Long.class);
        TYPE_MAPPING.add(long.class);
        TYPE_MAPPING.add(Short.class);
        TYPE_MAPPING.add(short.class);
        TYPE_MAPPING.add(Boolean.class);
        TYPE_MAPPING.add(boolean.class);
        TYPE_MAPPING.add(Character.class);
        TYPE_MAPPING.add(char.class);
        TYPE_MAPPING.add(Byte.class);
        TYPE_MAPPING.add(byte.class);
    }
}
