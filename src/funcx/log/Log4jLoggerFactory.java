package funcx.log;

public class Log4jLoggerFactory implements LoggerFactory {

    public Logger getLogger(Class<?> clazz) {
        return  new Log4jLogger(clazz);
    }

    public Logger getLogger(String name) {
        return new Log4jLogger(name);
    }

}
