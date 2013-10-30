package funcx.mvc;

import java.lang.reflect.Method;

/**
 * FuncX 控制器模型
 * 
 * <p>
 * MVC 核心 <br/>
 * instance 控制器实例 <br/>
 * method 控制器方法 <br/>
 * arguments 控制器参数类型
 * 
 * @author Sylthas
 * 
 */
public class Controller {

    public final Object instance;
    public final Method method;
    public final Class<?>[] arguments;

    public Controller(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.arguments = method.getParameterTypes();
    }
}
