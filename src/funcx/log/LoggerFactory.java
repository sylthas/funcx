package funcx.log;

public interface LoggerFactory {

    Logger getLogger(Class<?> clazz);

    Logger getLogger(String name);
}
