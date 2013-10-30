package funcx.comm;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Bean 包装器
 * 
 * <p>通过反射设置和获取对象属性
 * 
 * @author Sylthas
 *
 */
public class BeanWrapper {

    private Object bean;

    public BeanWrapper(Object bean) {
        this.bean = bean;
    }

    /**
     * 设置属性值
     * 
     * @param name 属性名称
     * @param value 值
     */
    public void setPropertyValue(String name, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, this.bean
                    .getClass());
            Method method = pd.getWriteMethod();
            method.setAccessible(true);
            method.invoke(this.bean, value);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得属性值
     * 
     * @param name 属性名称
     * @return
     */
    public Object getPropertyValue(String name) {
        Object value = null;
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, this.bean
                    .getClass());
            Method method = pd.getReadMethod();
            method.setAccessible(true);
            value = method.invoke(this.bean);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }
}
