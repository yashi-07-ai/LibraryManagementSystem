import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Retain at runtime for reflection
@Retention(RetentionPolicy.RUNTIME)
// Apply to fields, parameters, and methods
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface NotNull {
    String message() default "This field cannot be null";
}
