package funcx.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入注解
 * 
 * <p>
 * 用来标识需要注入的字段,像Controller中的Service.Service中的Dao...
 * 
 * @author Sylthas
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Inject {
    String value() default "";
}