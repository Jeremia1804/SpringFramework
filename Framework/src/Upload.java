package annote;
import java.lang.annotation.*;
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Upload{
    String value() default "jeremia"; 
}  