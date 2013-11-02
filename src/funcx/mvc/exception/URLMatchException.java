package funcx.mvc.exception;

/**
 * URL 参数匹配异常
 * 
 * <p>
 * 比如用户一个URL中配置两次重复的参数索引 /xx/$1/$1
 * 
 * @author Sylthas
 * 
 */
public class URLMatchException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public URLMatchException() {
    }

    public URLMatchException(String msg) {
        super(msg);
    }

    public URLMatchException(Throwable cause) {
        super(cause);
    }

    public URLMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
