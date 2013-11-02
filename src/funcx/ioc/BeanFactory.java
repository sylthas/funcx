package funcx.ioc;

import java.util.HashMap;
import java.util.Map;

import funcx.util.BeanUtils;

/**
 * Bean管理工厂
 * 
 * <p>
 * 管理BeanMapping,指定proto=true将获取new产生新的对象
 * 
 * @author Sylthas
 * 
 */
public class BeanFactory {

    /** 原型存储Map **/
    private static Map<String, Class<?>> protoMap = new HashMap<String, Class<?>>();

    private BeanFactory() {
    }

    public static void setBean(String id, Object bean) {
        BeanMapping.put(id, bean);
        protoMap.put(id, bean.getClass());
    }

    public static void setBean(String id, String fullClassName) {
        Object bean = BeanUtils.getInstance(fullClassName);
        BeanMapping.put(id, bean);
        protoMap.put(id, bean.getClass());
    }

    public static void setBean(String id, Class<?> clazz) {
        Object bean = BeanUtils.getInstance(clazz);
        BeanMapping.put(id, bean);
        protoMap.put(id, clazz);
    }

    public static Object getBean(String id) {
        return BeanMapping.get(id);
    }

    public static Object getBean(String id, boolean proto) {
        if (proto) {
            Class<?> clazz = protoMap.get(id);
            if (clazz != null)
                return BeanUtils.getInstance(clazz);
            return null;
        } else {
            return BeanMapping.get(id);
        }
    }

    public static <T> T getBean(String id, Class<T> clazzType) {
        T bean = null;
        Object obj = BeanMapping.get(id);
        if (obj != null && clazzType.isInstance(obj)) {
            bean = clazzType.cast(obj);
        }
        return bean;
    }

    public static <T> T getBean(String id, Class<T> clazzType, boolean proto) {
        T bean = null;
        if (proto) {
            Class<?> clazz = protoMap.get(id);
            if (clazz != null) {
                Object obj = BeanUtils.getInstance(clazz);
                if (clazzType.isInstance(obj))
                    bean = clazzType.cast(obj);
            }
        } else {
            bean = getBean(id, clazzType);
        }
        return bean;
    }

    public static boolean contains(String id) {
        return BeanMapping.get(id) != null;
    }

    public static Class<?> getType(String id) {
        return protoMap.get(id);
    }

    public static boolean matchType(String id, Class<?> clazzType) {
        return clazzType.equals(protoMap.get(id));
    }

    public static void clear() {
        protoMap.clear();
        BeanMapping.getBeanMap().clear();
    }
}
