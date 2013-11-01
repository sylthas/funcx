package funcx.mvc.template;

import funcx.log.Logger;
import funcx.mvc.Config;

/**
 * 模板工厂
 * 
 * @author Sylthas
 *
 */
public abstract class TemplateFactory {

    private static TemplateFactory instance;

    public static void setTemplateFactory(TemplateFactory templateFactory) {
        instance = templateFactory;
        Logger.getLogger(TemplateFactory.class).info(
                "TemplateFactory is set to: " + instance);
    }

    public static TemplateFactory getTemplateFactory() {
        return instance;
    }

    public static TemplateFactory createTemplateFactory(String name) {
        TemplateFactory tf = tryInitTemplateFactory(name);
        if (tf == null)
            tf = tryInitTemplateFactory(TemplateFactory.class.getPackage()
                    .getName()
                    + ".impl."
                    + name
                    + TemplateFactory.class.getSimpleName());
        if (tf == null) {
            Logger.getLogger(TemplateFactory.class).warn(
                    "Cannot init template factory '" + name + "'.");
            throw new IllegalArgumentException("Cannot init template factory '"
                    + name + "'.");
        }
        return tf;
    }

    static TemplateFactory tryInitTemplateFactory(String clazz) {
        try {
            Object obj = Class.forName(clazz).newInstance();
            if (obj instanceof TemplateFactory)
                return (TemplateFactory) obj;
        } catch (Exception e) {
        }
        return null;
    }

    public abstract void init(Config config);

    public abstract Template loadTemplate(String path) throws Exception;
}
