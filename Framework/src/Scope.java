package annote;
import java.lang.annotation.*;
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope{
    String value() default "andraina";
}  