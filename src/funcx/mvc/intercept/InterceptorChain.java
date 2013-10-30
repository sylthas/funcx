package funcx.mvc.intercept;

import funcx.mvc.Execution;

/**
 * 拦截器链
 * 
 * <p>
 * 在拦截器链实现中最终执行,以此来控制执行前做些事.
 * 
 * @author Sylthas
 * 
 */
public interface InterceptorChain {
    void doChain(Execution exec) throws Exception;
}
