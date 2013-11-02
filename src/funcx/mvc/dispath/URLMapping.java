package funcx.mvc.dispath;

import java.util.HashMap;
import java.util.Map;

import funcx.mvc.contrl.Controller;

/**
 * URL映射存储
 * 
 * <p>
 * 存放URL和控制器的映射Map
 * 
 * @author Sylthas
 * 
 */
public final class URLMapping {

    public static Map<URLMatcher, Controller> urlMaping = new HashMap<URLMatcher, Controller>();

    private URLMapping() {
    }

    public static void put(URLMatcher matcher, Controller contrl) {
        urlMaping.put(matcher, contrl);
    }

    public static Controller get(URLMatcher matcher) {
        return urlMaping.get(matcher);
    }

    public static Map<URLMatcher, Controller> getMapping() {
        return urlMaping;
    }
}
