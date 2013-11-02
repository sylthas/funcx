package funcx.mvc.render;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import funcx.mvc.template.TemplateFactory;

/**
 * 模板渲染器
 * 
 * <p>
 * 渲染各种模板, Jsp , Freemaker..
 * 
 * @author Sylthas
 * 
 */
public class TemplateRenderer extends Renderer {

    private String path;
    private Map<String, Object> model;

    public TemplateRenderer(String path) {
        this.path = path;
        this.model = new HashMap<String, Object>();
    }

    public TemplateRenderer(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public TemplateRenderer(String path, String modelKey, Object modelValue) {
        this.path = path;
        this.model = new HashMap<String, Object>();
        this.model.put(modelKey, modelValue);
    }

    @Override
    public void render(ServletContext context, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        TemplateFactory.getTemplateFactory().loadTemplate(path)
                .render(request, response, model);
    }
}
