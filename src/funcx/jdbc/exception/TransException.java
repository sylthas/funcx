package funcx.jdbc.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 事务异常<br/>
 * 
 * <p>
 * 主要用于包装SQLException. Runnable只能抛出RuntimeException及其子类异常
 * 
 * @author Sylthas
 * 
 */
public class TransException extends RuntimeException {

    private static final long serialVersionUID = 2081784607658710036L;

    private Throwable t;

    public TransException(Throwable t) {
        this.t = t;
    }

    @Override
    public synchronized Throwable getCause() {
        return t.getCause();
    }

    @Override
    public String getLocalizedMessage() {
        return t.getLocalizedMessage();
    }

    @Override
    public String getMessage() {
        return t.getMessage();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return t.getStackTrace();
    }

    @Override
    public void printStackTrace() {
        t.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        t.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        t.printStackTrace(s);
    }

    @Override
    public String toString() {
        return t.toString();
    }
}
