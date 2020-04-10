package ki.springmvc.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author fanxuebo
 * @description 表明哪些用户拥有访问该Handler方法的权限（注解配置用户名）
 * @company ki
 * @createDate 2020-04-08 08:39:50 星期三
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MySecurity {
    String[] value() default {};
}
