package funcx.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 共通辅助工具类
 * 
 * <p>
 * 一些常用又不知道放哪儿的方法
 * 
 * @author Sylthas
 * 
 */
public class Utils {

    /**
     * 获取项目根路径
     * 
     * @return
     */
    public static String getWebRootPath() {
        try {
            String path = Utils.class.getResource("/").toURI().getPath();
            return new java.io.File(path).getParentFile().getParentFile()
                    .getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取字节码文件存放路径
     * 
     * @return
     */
    public static String getClassPath() {
        try {
            String path = Utils.class.getClassLoader().getResource("").toURI()
                    .getPath();
            return new java.io.File(path).getAbsolutePath();
        } catch (Exception e) {
            String path = Utils.class.getClassLoader().getResource("")
                    .getPath();
            return new java.io.File(path).getAbsolutePath();
        }
    }

    /**
     * 加载配置文件
     * 
     * @param name
     * @return
     */
    public static java.util.Properties loadProperties(String name) {
        java.util.Properties prop = new java.util.Properties();
        try {
            prop.load(Utils.class.getClassLoader().getResourceAsStream(name));
        } catch (java.io.IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("The file '" + name
                    + "' not found ! ");
        }
        return prop;
    }

    /**
     * 获取请求URI
     * 
     * <p>
     * 去除项目路径
     * 
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }

    /**
     * 获取用户接入IP
     * 
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "Local";
        }
        return ip;
    }

    /**
     * 获取项目真实路径
     * 
     * @param request
     * @return
     */
    public static String getRealPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

}
