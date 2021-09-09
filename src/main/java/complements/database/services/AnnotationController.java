package complements.database.services;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

// TODO convertName methods should be not here...
// TODO methods with Object param are unnecessary

public class AnnotationController {

    public boolean isAnnotated(Object o, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(o) && o.getClass().isAnnotationPresent(annotationClass));
    }

    public boolean isAnnotated(Field field, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(field) && field.isAnnotationPresent(annotationClass));
    }

    public boolean isAnnotated(Class<?> cl, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(cl) && cl.isAnnotationPresent(annotationClass));
    }

    /*public String getTableStatement(Class<?> cl) {
        StringBuilder b = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        b.append(getDBName(cl)).append(" (");

        for (Field field : cl.getDeclaredFields()) {
            if (!isAnnotated(field, Skip.class)) {
                // TODO handle @Column
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
    }*/

}
