package funcx.ioc;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean 存储Map
 * 
 * <p>
 * 这里存放单例的Bean实体,想要就获取.
 * 
 * @author Sylthas
 * 
 */
public final class BeanMapping {

    private static Map<String, Object> beanMap = new HashMap<String, Object>();

    private BeanMapping() {
    }

    public static void put(String key, Object value) {
        beanMap.put(key, value);
    }

    public static Object get(String key) {
        return beanMap.get(key);
    }

    public static Map<String, Object> getBeanMap() {
        return beanMap;
    }
}
