package funcx.ioc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import funcx.ioc.annotation.Controller;
import funcx.ioc.annotation.Inject;
import funcx.ioc.annotation.Repository;
import funcx.ioc.annotation.Service;
import funcx.ioc.beans.BeanDefinition;
import funcx.ioc.beans.PropertyDefinition;

/**
 * Bean信息存储容器
 * 
 * <p>
 * 存储Bean的描述信息
 * 
 * @author Sylthas
 * 
 */
public final class BeanDefinitionList {

    private static List<BeanDefinition> defineList = new ArrayList<BeanDefinition>();

    private BeanDefinitionList() {
    }

    public static void add(BeanDefinition bd) {
        defineList.add(bd);
    }

    public static void add(String id, String fullClassName) {
        add(new BeanDefinition(id, fullClassName));
    }

    public static void add(Class<?> clazz) {
        String id = getBeanId(clazz);
        String className = clazz.getName();
        BeanDefinition bd = new BeanDefinition(id, className);

        /**
         * 遍历字段若又@Inject注解则添加ProperttyDefinition信息
         */
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                PropertyDefinition pd = new PropertyDefinition(field.getName(),
                        id);
                bd.getProperties().add(pd);
            }
        }
        add(bd);

    }

    /** 获取全部的Bean定义信息 **/
    public static List<BeanDefinition> getBeanDefinitions() {
        return defineList;
    }

    /** 获取指定ID的Bean定义信息 **/
    public static BeanDefinition getBeanDefinition(String id) {
        for (BeanDefinition bd : defineList) {
            if (bd.getId().equals(id))
                return bd;
        }
        return null;
    }

    /** 获取指定类的Bean定义信息 **/
    public static BeanDefinition getBeanDefinition(Class<?> clazz) {
    	for (BeanDefinition bd : defineList) {
            if (bd.getClassName().equals(clazz.getName()))
                return bd;
        }
        return null;
    }

    static String getBeanId(Class<?> clazz) {
        String id = null;
        if (clazz.isAnnotationPresent(Controller.class)) {
            id = clazz.getAnnotation(Controller.class).value();
        } else if (clazz.isAnnotationPresent(Service.class)) {
            id = clazz.getAnnotation(Service.class).value();
        } else if (clazz.isAnnotationPresent(Repository.class)) {
            id = clazz.getAnnotation(Repository.class).value();
        }
        // 如果没有设置Bean ID 则按照类名称转换 UserDao --> userDao
        if (id == null || "".equals(id)) {
            String className = clazz.getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            id = className.substring(0, 1).toLowerCase()
                    + className.substring(1);
        }
        return id;
    }
}
