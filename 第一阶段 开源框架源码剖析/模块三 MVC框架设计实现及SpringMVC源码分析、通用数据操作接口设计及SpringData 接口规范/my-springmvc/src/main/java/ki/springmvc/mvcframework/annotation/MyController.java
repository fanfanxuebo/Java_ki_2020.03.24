package ki.springmvc.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-05 18:36:18 星期日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String value() default "";
}
