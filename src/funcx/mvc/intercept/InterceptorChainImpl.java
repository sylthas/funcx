package funcx.mvc.intercept;

import funcx.mvc.execution.Execution;

/**
 * 拦截器链的实现
 * 
 * <p>
 * 执行拦截器链,若是最后一个拦截器则执行处理,否则继续执行拦截器链
 * 
 * @author Sylthas
 * 
 */
public class InterceptorChainImpl implements InterceptorChain {

    private final Interceptor[] intercptors;
    private int index = 0;
    private Object result = null;

    InterceptorChainImpl(Interceptor[] interceptors) {
        this.intercptors = interceptors;
    }

    Object getResult() {
        return result;
    }

    @Override
    public void doChain(Execution exec) throws Exception {
        if (index == intercptors.length) {
            result = exec.execute();
        } else {
            index++;
            intercptors[index - 1].intercept(exec, this);
        }
    }

}
