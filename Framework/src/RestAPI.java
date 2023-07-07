package annote;
import java.lang.annotation.*;
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RestAPI{
    String value() default "andraina"; 
}  