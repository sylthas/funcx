package funcx.mvc.dispath;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import funcx.comm.BeanWrapper;
import funcx.comm.Const;
import funcx.comm.Converter;
import funcx.log.Logger;
import funcx.mvc.annotation.Parameter;
import funcx.util.BeanUtils;
import funcx.util.DateUtils;

/**
 * 参数注入辅助
 * 
 * <p>
 * 参数注入辅助实现...<br/>
 * 日期注入需要使用yyyy-MM-dd格式输入,不支持数组注入..
 * 
 * @author Sylthas
 * 
 */
public class ParamInject {

    private static Logger log = Logger.getLogger(ParamInject.class);

    public static Map<String, String> getParamsMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Map<?, ?> p = request.getParameterMap();
        for (Object key : p.keySet()) {
            map.put(key.toString(), ((String[]) p.get(key))[0]);
        }
        return map;
    }

    public static void inject(Object bean, Map<String, String> params) {
        Class<?> clazz = bean.getClass();
        BeanWrapper bw = new BeanWrapper(bean);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Parameter.class)) {
                if (TYPE_MAPPING.contains(field.getType())) {
                    doInject(bw, field, params);
                } else {
                    Object sub = BeanUtils.getInstance(field.getType());
                    BeanWrapper bwb = new BeanWrapper(sub);
                    Class<?> bzz = sub.getClass();
                    Field[] fbs = bzz.getDeclaredFields();
                    for (Field fb : fbs) {
                        doInject(bwb, fb, params);
                    }
                    bw.setPropertyValue(field.getName(), sub);
                }
            }
        }
    }

    /** 执行注入 **/
    private static void doInject(BeanWrapper bw, Field field, Map<String, String> map) {
        if (map.isEmpty())
            return;
        String value = map.get(field.getName());
        if (value == null || "".equals(value))
            return;
        // 如果是基本数据类型和其包装类
        if (TYPE_MAPPING.contains(field.getType())) {
            bw.setPropertyValue(field.getName(),
                    Converter.parse(value, field.getType()));
        } else if (String.class.equals(field.getType())) {
            bw.setPropertyValue(field.getName(), value);
        } else if (java.util.Date.class.equals(field.getType())) {
            try {
                bw.setPropertyValue(field.getName(),
                        DateUtils.parse(value, Const.DEFAULT_DATE_PATTERN));
            } catch (ParseException e) {
                log.error("Convert value '" + value + "' to Date failed !", e);
            }
        }
        map.remove(field.getName());
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
