package funcx.mvc;

import javax.servlet.ServletContext;

/**
 * 配置接口
 * 
 * <p>
 * 用于分发器初始化..
 * 
 * @author Sylthas
 * 
 */
public interface Config {

    public ServletContext getServletContext();

    public String getInitParameter(String name);
}
