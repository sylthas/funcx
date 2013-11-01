package funcx.mvc.execution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理接口
 * 
 * <p>
 * 通过实现该接口来处理异常情况
 * 
 * @author Sylthas
 *
 */
public interface ExceptionHandler {
    void handle(HttpServletRequest request, HttpServletResponse response,
            Exception e) throws Exception;
}
