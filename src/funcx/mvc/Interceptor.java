package funcx.mvc;

import funcx.mvc.intercept.InterceptorChain;

/**
 * FuncX 拦截器
 * 
 * <p>
 * MVC 核心 <br/>
 * 在拦截器中可以快速的开启数据库事务或是其他的处理<br/>
 * 
 * @author Sylthas
 * 
 */
public interface Interceptor {
    void intercept(Execution exec, InterceptorChain chain) throws Exception;
}
