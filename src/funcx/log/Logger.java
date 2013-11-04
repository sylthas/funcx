package funcx.log;

/**
 * FuncX 日志
 * 
 * <p>
 * 又是一个小轮子
 * 
 * @author Sylthas
 * 
 */
public abstract class Logger {

    private static LoggerFactory factory;

    static {
        if (factory == null) {
            try {
                Class.forName("org.apache.log4j.Logger");
                factory = new Log4jLoggerFactory();
            } catch (Exception e) {
                factory = new JdkLoggerFactory();
                factory.getLogger(Logger.class).warn("You are using default simple JdkLogger! Don't use it in Production environment!");
            }
        }
    }

    public static void setLoggerFactory(LoggerFactory loggerFactory) {
        if (loggerFactory != null)
            Logger.factory = loggerFactory;
    }

    public static Logger getLogger(Class<?> clazz) {
        return factory.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return factory.getLogger(name);
    }

    public abstract void debug(String message);

    public abstract void debug(String message, Throwable t);

    public abstract void info(String message);

    public abstract void info(String message, Throwable t);

    public abstract void warn(String message);

    public abstract void warn(String message, Throwable t);

    public abstract void error(String message);

    public abstract void error(String message, Throwable t);

    public abstract void fatal(String message);

    public abstract void fatal(String message, Throwable t);

    public abstract boolean isDebugEnabled();

    public abstract boolean isInfoEnabled();

    public abstract boolean isWarnEnabled();

    public abstract boolean isErrorEnabled();

    public abstract boolean isFatalEnabled();
}
