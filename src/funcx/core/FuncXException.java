package funcx.core;

/**
 * 系统异常
 * 
 * <p>
 * FuncX 框架系统异常
 * 
 * @author Sylthas
 * 
 */
public class FuncXException extends RuntimeException {

    private static final long serialVersionUID = 2600025136124001522L;

    public FuncXException() {
        super();
    }

    public FuncXException(String msg) {
        super(msg);
    }

    public FuncXException(Throwable cause) {
        super(cause);
    }

    public FuncXException(String message, Throwable cause) {
        super(message, cause);
    }
}
