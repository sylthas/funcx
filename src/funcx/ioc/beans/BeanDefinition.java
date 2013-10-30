package funcx.ioc.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean定义信息
 * 
 * <p>
 * 描述一个JavaBean的基本信息
 * 
 * @author Sylthas
 * 
 */
public class BeanDefinition {

    private String id;
    private String className;
    private List<PropertyDefinition> properties = new ArrayList<PropertyDefinition>();

    /**
     * 构造函数
     * 
     * @param id 唯一标识<Bean ID>
     * @param className 类全名
     */
    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PropertyDefinition> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDefinition> properties) {
        this.properties = properties;
    }
}
