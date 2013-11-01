package funcx.mvc.execution;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 默认的异常处理接口实现
 * 
 * <p>
 * 处理控制器执行中发生的异常
 * @author Sylthas
 *
 */
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(HttpServletRequest request,
            HttpServletResponse response, Exception e) throws Exception {
        PrintWriter pw = response.getWriter();
        pw.write("<html><head><title>Exception</title></head><body><pre>");
        e.printStackTrace(pw);
        pw.write("</pre></body></html>");
        pw.flush();
        pw.close();

    }

}
