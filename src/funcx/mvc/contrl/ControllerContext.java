package funcx.mvc.contrl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器上下文
 * 
 * <p>
 * 在控制器中访问request,response
 * 
 * @author Sylthas
 * 
 */
public class ControllerContext {

    private static final ThreadLocal<ControllerContext> threadContrlContext = new ThreadLocal<ControllerContext>();
    private ServletContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletContext getServletContext() {
        return context;
    }

    public HttpServletRequest getHttpServletRequest() {
        return request;
    }

    public HttpServletResponse getHttpServletResponse() {
        return response;
    }

    public static ControllerContext get() {
        return threadContrlContext.get();
    }

    public static void set(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) {
        ControllerContext ctx = new ControllerContext();
        ctx.context = context;
        ctx.request = request;
        ctx.response = response;
        threadContrlContext.set(ctx);
    }

    public static void remove() {
        threadContrlContext.remove();
    }
}
