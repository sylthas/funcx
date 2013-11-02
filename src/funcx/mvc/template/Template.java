package funcx.mvc.template;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模板接口
 * 
 * <p>
 * 实现各种模板的接入..
 * 
 * @author Sylthas
 * 
 */
public interface Template {
    void render(HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) throws Exception;
}
