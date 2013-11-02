package funcx.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import funcx.comm.BeanWrapper;


/**
 * JavaBean 辅助工具类
 * 
 * <p>
 * 对JavaBean的一些辅助操作
 * 
 * @version 1.0
 * @author Sylthas
 *
 */
public abstract class BeanUtils {

    /**
     * 获取对象实例
     * 
     * @param clazz
     * @return
     */
    public static <T> T getInstance(Class<T> clazz) {
        if (!clazz.isInterface()) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取对象实例
     * 
     * @param fullClassName 对象类全名
     * @return
     */
    public static Object getInstance(String fullClassName) {
        try {
            Class<?> clazz = Class.forName(fullClassName);
            return getInstance(clazz);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 复制相同属性值
     * 
     * @param bean 源
     * @param target 目标
     */
    public static void copyProperties(Object bean, Object target) {
        Class<?> b = bean.getClass();
        Class<?> t = target.getClass();
        Field[] bFields = b.getDeclaredFields();
        Field[] tFields = t.getDeclaredFields();
        BeanWrapper bw = new BeanWrapper(bean);
        BeanWrapper bwt = new BeanWrapper(target);
        for (Field ft : tFields) {
            for (Field fb : bFields) {
                if (ft.getName().equals(fb.getName())
                        && ft.getType().equals(fb.getType())) {
                    bwt.setPropertyValue(ft.getName(),
                            bw.getPropertyValue(fb.getName()));
                }
            }
        }
    }

    /**
     * 将Map集合转换为对象
     * 
     * @param map
     * @param clazz 目标对象classType
     * @return
     */
    public static <T> T parse(Map<String, Object> map, Class<T> clazz) {
        Set<String> keys = map.keySet();
        T bean = getInstance(clazz);
        BeanWrapper bw = new BeanWrapper(bean);
        for (String key : keys) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().toLowerCase().equals(
                        key.toLowerCase())) {
                    bw.setPropertyValue(field.getName(), map.get(key));
                }
            }
		}
    	return bean;
    }

}
