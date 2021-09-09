package application.helpers;

import complements.PropertiesHandler;
import complements.annotations.Column;
import complements.annotations.Table;

import java.io.File;
import java.lang.reflect.Field;

public class Transformer {

    private static Transformer transformer;

    private Transformer() {}

    public static Transformer getInstance() {
        if (transformer == null) {
            transformer = new Transformer();
        }
        return transformer;
    }

    public String getClassName(String path) {
        String rootDir = PropertiesHandler.getInstance().getProperty("project-source-dir") + File.separatorChar;
        int dot = path.lastIndexOf('.');
        int root = path.lastIndexOf(rootDir) + rootDir.length();
        return path.substring(root, dot).replace(File.separatorChar, '.');
    }

    public <T extends Enum<T>> int getDBIndex(Enum<T> enumToUse) {
        return enumToUse.ordinal() + 1;
    }

    public <T extends Enum<T>> String getDBName(Enum<T> enumToUse, boolean isEnumInDB) {
        String name = enumToUse.name().toLowerCase();

        if (isEnumInDB) {
            return name.replace("_", " ");
        }

        return name;
    }

    public String getDBName(Class<?> className) {
        String name = className.getAnnotation(Table.class).name();
        if (name.isBlank()) {
            return convertName(className.getSimpleName());
        }
        return name;
    }

    public String getDBName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getAnnotation(Column.class).name();
            if (!name.isBlank()) {
                return name;
            }
        }
        return convertName(field.getName());
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

}
