package ki.spring.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author fanxuebo
 * @description
 * @company ki
 * @createDate 2020-04-03 08:52:29 星期五
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyService {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
