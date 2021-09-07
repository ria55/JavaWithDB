package application.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

public class AnnotationController {

    public boolean isAnnotated(Object o, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(o) && o.getClass().isAnnotationPresent(annotationClass));
    }

    public boolean isAnnotated(Field field, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(field) && field.isAnnotationPresent(annotationClass));
    }

    private String convertName(String javaName) {
        StringBuilder b = new StringBuilder(javaName.substring(0, 1).toLowerCase());
        for (int i = 1; i < javaName.length(); i++) {
            char c = javaName.charAt(i);
            if (Character.isUpperCase(c)) {
                b.append("_");
                c = Character.toLowerCase(c);
            }
            b.append(c);
        }
        return b.toString();
    }

    private String getDBName(Field field) {
        String name = field.getAnnotation(Column.class).name();
        if (name.isBlank()) {
            return convertName(field.getName());
        }
        return name;
    }

    private String getDBName(Object object) {
        String name = object.getClass().getAnnotation(Table.class).name();
        if (name.isBlank()) {
            return convertName(object.getClass().getSimpleName());
        }
        return name;
    }

    public String getTableStatement(Object o) {
        Class<?> cl = o.getClass();
        StringBuilder b = new StringBuilder("CREATE TABLE IF NOT EXISTS " + getDBName(o));

        for (Field field : cl.getDeclaredFields()) {
            field.setAccessible(true);
            if (!isAnnotated(field, Skip.class)) {
                if (isAnnotated(field, Column.class)) {
                    b.append("\n").append(getDBName(field));
                }
            }
        }

        return b.toString();
    }

}
