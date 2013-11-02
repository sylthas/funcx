package funcx.mvc.template.impl;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import funcx.mvc.template.Template;

/**
 * JSP渲染模板
 * 
 * @author Sylthas
 * 
 */
public class JspTemplate implements Template {

    private String path;

    public JspTemplate(String path) {
        this.path = path;
    }

    public void render(HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model)
            throws Exception {
        Set<String> keys = model.keySet();
        for (String key : keys) {
            request.setAttribute(key, model.get(key));
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

}
