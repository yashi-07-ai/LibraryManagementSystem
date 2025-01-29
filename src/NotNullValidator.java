import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class NotNullValidator {
    public static void validate(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();

        // Iterate through fields
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true); // Access private fields
                Object value = field.get(obj);
                if (value == null) {
                    NotNull annotation = field.getAnnotation(NotNull.class);
                    throw new Exception(annotation.message());
                }
            }
        }
    }
}
