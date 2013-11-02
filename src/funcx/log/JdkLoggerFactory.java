package funcx.log;

/**
 * JDK 日志工厂
 * 
 * @author Sylthas
 * 
 */
public class JdkLoggerFactory implements LoggerFactory {

    public Logger getLogger(Class<?> clazz) {
        return new JdkLogger(clazz);
    }

    public Logger getLogger(String name) {
        return new JdkLogger(name);
    }

}
