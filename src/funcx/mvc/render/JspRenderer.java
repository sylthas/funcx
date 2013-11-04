package funcx.mvc.render;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Jsp渲染实现
 * 
 * <p>
 * 不是用模板就使用这个吧...
 * 
 * @author Sylthas
 *
 */
public class JspRenderer extends Renderer {

    private String path;
    private String dispath = "forward";
    private Map<String, Object> model;

    public static final String FORWARD = "forward";
    public static final String REDIRECT = "redirect";

    public JspRenderer(String path) {
        this.path = path;
    }

    public JspRenderer(String path, String dispath) {
        this.path = path;
        this.dispath = dispath;
    }

    public JspRenderer(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public JspRenderer(String path, String dispath, Map<String, Object> model) {
        this.path = path;
        this.dispath = dispath;
        this.model = model;
    }

    @Override
    public void render(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (model != null) {
            Set<String> keys = model.keySet();
            for (String key : keys) {
                request.setAttribute(key, model.get(key));
            }
        }
        if (FORWARD.equals(dispath)) {
            request.getRequestDispatcher(path).forward(request, response);
        } else if (REDIRECT.equals(dispath)) {
            response.sendRedirect(request.getContextPath() + path);
        }

    }

}
