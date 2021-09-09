package application.helpers;

import complements.annotations.Column;
import complements.annotations.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Transformer {

    public static <T extends Enum<T>> int getDBIndex(Enum<T> enumToUse) {
        return enumToUse.ordinal() + 1;
    }

    public static <T extends Enum<T>> String getDBName(Enum<T> enumToUse, boolean isEnumInDB) {
        String name = enumToUse.name().toLowerCase();

        if (isEnumInDB) {
            return name.replace("_", " ");
        }

        return name;
    }

    public static String getDBName(Class<?> className) {
        String name = className.getAnnotation(Table.class).name();
        if (name.isBlank()) {
            return convertName(className.getSimpleName());
        }
        return name;
    }

    public static String getDBName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getAnnotation(Column.class).name();
            if (!name.isBlank()) {
                return name;
            }
        }
        return convertName(field.getName());
    }

    private static String convertName(String javaName) {
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

}
