package funcx.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * URL 映射注解
 * 
 * <p>
 * 将URL映射到具体控制器的具体方法
 * 
 * @author Sylthas
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequestMapping {
    String value() default "";
}