package funcx.jdbc.exception;

public class JdbcException extends RuntimeException {

    private static final long serialVersionUID = -9106248349916059993L;

    public JdbcException() {
        super();
    }

    public JdbcException(String msg) {
        super(msg);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

}
