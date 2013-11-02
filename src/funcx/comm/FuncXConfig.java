package funcx.comm;

import java.io.IOException;
import java.util.Properties;


/**
 * FuncX 框架配置文件
 * 
 * <p>
 * 读取classpath路径下的funcx.properties配置文件.<br/>
 * 该文件存放框架配置信息.
 * 
 * @author Sylthas
 * 
 */
public class FuncXConfig {

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(FuncXConfig.class.getClassLoader().getResourceAsStream(
                    Const.DEFAULT_PROPERTIES_FILE));
        } catch (NullPointerException e) {
            throw new FuncXException("Not found file - '"
                    + Const.DEFAULT_PROPERTIES_FILE + "' !");
        } catch (IOException e) {
            throw new FuncXException("Load file - '"
                    + Const.DEFAULT_PROPERTIES_FILE + "' error !", e);
        }
    }

    public static String get(String key) {
        return String.valueOf(prop.getProperty(key));
    }

    public static String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
}
