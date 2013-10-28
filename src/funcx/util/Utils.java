package funcx.util;

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
}
