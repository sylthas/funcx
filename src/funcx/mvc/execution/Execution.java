package funcx.mvc.execution;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import funcx.mvc.annotation.ResponseBody;
import funcx.mvc.contrl.Controller;
import funcx.util.Json;

/**
 * FuncX 执行器
 * 
 * <p>
 * MVC 核心 <br/>
 * 构造函数传入控制器和参数数组<br/>
 * 执行execute方法返回执行结果<br/>
 * 
 * @author Sylthas
 * 
 */
public class Execution {

    public final HttpServletRequest request;
    public final HttpServletResponse response;
    private final Controller contrl;
    private final Object[] args;

    public Execution(HttpServletRequest request, HttpServletResponse response,
            Controller contrl, Object[] args) {
        this.request = request;
        this.response = response;
        this.contrl = contrl;
        this.args = args;
    }

    public Object execute() throws Exception {
        try {
            Object result = contrl.method.invoke(contrl.instance, args);
            if (contrl.method.isAnnotationPresent(ResponseBody.class))
            	return Json.toJson(result);
            return result;
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t != null && t instanceof Exception)
                throw (Exception) t;
            throw e;
        }
    }
}
