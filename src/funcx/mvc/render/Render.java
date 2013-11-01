package funcx.mvc.render;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FuncX 渲染器
 * 
 * <p>
 * MVC 核心 <br/>
 * 对执行结果进行渲染...
 * 
 * @author Sylthas
 * 
 */
public abstract class Render {

    protected String contentType;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public abstract void render(ServletContext context,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
