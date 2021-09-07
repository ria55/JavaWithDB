package application.annotations;

import application.helpers.SQLType;

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
        if (isAnnotated(field, Column.class)) {
            String name = field.getAnnotation(Column.class).name();
            if (!name.isBlank()) {
                return name;
            }
        }
        return convertName(field.getName());
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
        StringBuilder b = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        b.append(getDBName(o)).append(" (");

        for (Field field : cl.getDeclaredFields()) {
            //field.setAccessible(true);
            if (!isAnnotated(field, Skip.class)) {
                b.append("\n\t")
                        .append(getDBName(field))
                        .append(" ")
                        .append(SQLType.convertType(field.getType().getSimpleName()))
                        .append(",");
            }
        }

        b.setLength(b.length() - 1);
        b.append("\n);");

        return b.toString();
    }

}
