package funcx.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 异常处理注解
 * 
 * <p>
 * 异常处理器实现必须是唯一的有且只有一个...
 * 
 * @author Sylthas
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandle {
    String value() default "";
}
