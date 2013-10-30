package funcx.ioc.beans;

/**
 * 字段定义信息
 * 
 * <p>
 * 标识一个Field对象的名字及饮用对象类别
 * 
 * @author Sylthas
 * 
 */
public class PropertyDefinition {

    private String name;
    private String ref;

    /**
     * 属性定义构造函数
     * 
     * @param name 属性名
     * @param ref 所属Bean的ID
     */
    public PropertyDefinition(String name, String ref) {
        this.name = name;
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
