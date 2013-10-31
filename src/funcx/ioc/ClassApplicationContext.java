package funcx.ioc;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import funcx.comm.BeanWrapper;
import funcx.comm.Const;
import funcx.comm.FuncXConfig;
import funcx.ioc.annotation.Controller;
import funcx.ioc.annotation.Repository;
import funcx.ioc.annotation.Service;
import funcx.ioc.beans.BeanDefinition;
import funcx.ioc.beans.PropertyDefinition;
import funcx.util.BeanUtils;
import funcx.util.PackageUtils;

/**
 * IoC容器 上下文
 * 
 * <p>
 * 承担IoC的扫描注入工作
 * 
 * @author Sylthas
 * 
 */
public class ClassApplicationContext {

    private static ClassApplicationContext instance = null;

    final Set<Class<?>> beans;

    private ClassApplicationContext() {
        beans = new HashSet<Class<?>>();
        init();
    }

    public static ClassApplicationContext getInstance() {
        if (instance == null)
            instance = new ClassApplicationContext();
        return instance;
    }

    private void init() {
        readAnnotationClass();
        instanceBeans();
        injectObject();
    }

    /** 读取注解的Class **/
    private void readAnnotationClass() {
        Class<?>[] clazzs = scanPackageClasses();
        if (clazzs != null && clazzs.length > 0) {
            for (Class<?> clazz : clazzs) {
                if (clazz.isAnnotationPresent(Controller.class)
                        || clazz.isAnnotationPresent(Service.class)
                        || clazz.isAnnotationPresent(Repository.class)) {
                    BeanDefinitionList.add(clazz);
                } else {
                    // 将其他的class放到集合中 其他地方调用处理
                    beans.add(clazz);
                }
            }
        }
    }

    /** 实例化Bean **/
    private void instanceBeans() {
        List<BeanDefinition> beanDefines = BeanDefinitionList
                .getBeanDefinitions();
        if (!beanDefines.isEmpty()) {
            for (BeanDefinition bd : beanDefines) {
                if (bd != null) {
                    BeanFactory.setBean(bd.getId(), bd.getClassName());
                }
            }
        }
    }

    /** 注入实体 **/
    private void injectObject() {
        List<BeanDefinition> beanDefines = BeanDefinitionList
                .getBeanDefinitions();
        for (BeanDefinition bd : beanDefines) {
            Object bean = BeanFactory.getBean(bd.getId());
            List<PropertyDefinition> pds = bd.getProperties();
            if (!pds.isEmpty()) {
                for (PropertyDefinition pd : pds) {
                    String name = pd.getName();
                    String ref = pd.getRef();
                    if (ref != null && !"".equals(ref)) {
                        BeanWrapper bw = new BeanWrapper(bean);
                        bw.setPropertyValue(name, BeanFactory.getBean(name));
                    }
                }
            }
        }
    }

    /** 扫描包路径 **/
    private Class<?>[] scanPackageClasses() {
        Class<?>[] clazzs = null;
        try {
            clazzs = PackageUtils.scan(FuncXConfig
                    .get(Const.DEFAULT_SCAN_PACKAGE));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazzs;
    }

    /** 原型注入,递归调用 **/
    private Object doInject(BeanDefinition bd) {
        if (bd != null) {
            Object bean = BeanUtils.getInstance(bd.getClassName());
            List<PropertyDefinition> pds = bd.getProperties();
            if (!pds.isEmpty()) {
                for (PropertyDefinition pd : pds) {
                    String name = pd.getName();
                    String ref = pd.getRef();
                    if (ref != null && !"".equals(ref)) {
                        BeanDefinition sbd = BeanDefinitionList
                                .getBeanDefinition(name);
                        Object sub = doInject(sbd);
                        BeanWrapper bw = new BeanWrapper(bean);
                        bw.setPropertyValue(name, sub);
                    }
                }
            }
            return bean;
        }
        return null;
    }

    /**
     * 获取单例Bean
     * 
     * <p>
     * 从内存中获取,方法线程安全
     * 
     * @param key
     * @return
     */
    public Object getBean(String key) {
        return BeanMapping.get(key);
    }

    /**
     * 获取原型Bean
     * 
     * <p>
     * new 一个对象,线程安全
     * 
     * @param key
     * @param proto 原型标识
     * @return
     */
    public Object getBean(String key, boolean proto) {
        if (proto) {
            BeanDefinition beanDefine = BeanDefinitionList
                    .getBeanDefinition(key);
            Object bean = doInject(beanDefine);
            return bean;
        } else {
            return getBean(key);
        }
    }

    /**
     * 获取指定注解的JavaBean
     * 
     * @param annotation 注解
     * @return
     */
    public Object[] getBean(Class<? extends Annotation> annotation) {
        List<Object> list = new ArrayList<Object>();
        if (annotation.isAnnotation()) {
            Iterator<Class<?>> it = beans.iterator();
            while (it.hasNext()) {
                Class<?> clazz = it.next();
                if (clazz.isAnnotationPresent(annotation)) {
                    Object bean = BeanUtils.getInstance(clazz);
                    list.add(bean);
                }
            }
        }
        return list.toArray(new Object[list.size()]);
    }

    /**
     * IOC注解以外的Class
     * 
     * @return
     */
    public Set<Class<?>> getClasses() {
        return beans;
    }

    /** 销毁 **/
    public void distroy() {
        beans.clear();
        BeanFactory.clear();
    }
}
