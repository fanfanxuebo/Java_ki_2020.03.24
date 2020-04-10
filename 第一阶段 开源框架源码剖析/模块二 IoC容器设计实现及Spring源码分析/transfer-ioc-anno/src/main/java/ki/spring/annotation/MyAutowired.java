package ki.spring.annotation;

import java.lang.annotation.*;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 08:56:50 星期五
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
    boolean required() default true;
}
