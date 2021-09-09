package complements.database.annotations;

import complements.database.SQLType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name() default "";
    SQLType type() default SQLType.DEFAULT;
    int length() default 255;
    boolean isNotNull() default false;
    boolean isUnique() default false;
    String defaultValue() default "";
}
