package funcx.mvc.template.impl;

import funcx.log.Logger;
import funcx.mvc.Config;
import funcx.mvc.template.Template;
import funcx.mvc.template.TemplateFactory;

/**
 * JSP模板工厂
 * 
 * @author Sylthas
 *
 */
public class JspTemplateFactory extends TemplateFactory {

    private Logger log = Logger.getLogger(getClass());

    public Template loadTemplate(String path) throws Exception {
        if (log.isDebugEnabled())
            log.debug("Load JSP template '" + path + "'.");
        return new JspTemplate(path);
    }

    public void init(Config config) {
        log.info("JspTemplateFactory init complete.");
    }

}
