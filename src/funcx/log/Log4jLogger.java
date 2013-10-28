package funcx.log;

import org.apache.log4j.Level;

/**
 * Log4j 日志
 * 
 * @author Sylthas
 * 
 */
public class Log4jLogger extends Logger {

    private org.apache.log4j.Logger log;

    Log4jLogger(Class<?> clazz) {
        log = org.apache.log4j.Logger.getLogger(clazz);
    }

    Log4jLogger(String name) {
        log = org.apache.log4j.Logger.getLogger(name);
    }

    public void debug(String message) {
        log.debug(message);

    }

    public void debug(String message, Throwable t) {
        log.debug(message, t);
    }

    public void info(String message) {
        log.info(message);

    }

    public void info(String message, Throwable t) {
        log.info(message, t);

    }

    public void warn(String message) {
        log.warn(message);

    }

    public void warn(String message, Throwable t) {
        log.warn(message, t);

    }

    public void error(String message) {
        log.error(message);

    }

    public void error(String message, Throwable t) {
        log.error(message, t);

    }

    public void fatal(String message) {
        log.fatal(message);

    }

    public void fatal(String message, Throwable t) {
        log.fatal(message, t);

    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return log.isEnabledFor(Level.WARN);
    }

    public boolean isErrorEnabled() {
        return log.isEnabledFor(Level.ERROR);
    }

    public boolean isFatalEnabled() {
        return log.isEnabledFor(Level.FATAL);
    }

}
