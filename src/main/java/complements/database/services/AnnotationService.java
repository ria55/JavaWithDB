package complements.database.services;

import application.helpers.Transformer;
import complements.database.SQLType;
import complements.database.annotations.Skip;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

// TODO convertName methods should be not here...
// TODO methods with Object param are unnecessary

public class AnnotationService {

    private final Transformer TRANS = Transformer.getInstance();

    public AnnotationService() {}

    public Map<String, List<String>> getAllTables(List<Class<?>> classes) {
        Map<String, List<String>> allTables = new HashMap<>();

        for (Class<?> cl : classes) {
            String tableName = TRANS.getDBName(cl);
            List<String> props = getTable(cl);
            allTables.put(tableName, props);
        }

        return allTables;
    }

    private List<String> getTable(Class<?> cl) {
        List<String> tableCols = new ArrayList<>();
        Field[] fields = cl.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].isAnnotationPresent(Skip.class)) {
                String col = getColSQL(fields[i]);
                if (i == fields.length - 1) {
                    col = col.substring(0, col.length() - 1);           // TODO there is a little bug in the system, tralalala...
                }
                tableCols.add(col);
            }
        }

        return tableCols;
    }

    private String getColSQL(Field field) {
        StringBuilder column = new StringBuilder(TRANS.getDBName(field) + " ");

        SQLType type = TRANS.getDBColType(field);
        int length = TRANS.getDBColLength(field, type);
        String isNotNull = TRANS.getDBColNull(field);
        String isUnique = TRANS.getDBColUnique(field);
        String defaultValue = TRANS.getDBColDefault(field);
        String pk = TRANS.getDBColPK(field);

        column.append(type.toString())
                .append(length > 0 ? "(" + length + ")" : "")
                .append(isNotNull.isBlank() ? "" : " " + isNotNull + " ")
                .append(isUnique.isBlank() ? "" : " " + isUnique + " ")
                .append(pk.isBlank() ? "" : " " + pk + " ")
                .append(defaultValue.isBlank() ? "" : " DEFAULT " + "'" + defaultValue + "'")
                .append(",");

        return column.toString();
    }

    @Deprecated
    private Properties getColProps(Field field) {
        Properties properties = new Properties();

        SQLType type = TRANS.getDBColType(field);

        properties.put("name", TRANS.getDBName(field));
        properties.put("type", type.toString());
        properties.put("length", TRANS.getDBColLength(field, type));
        properties.put("isNotNull", TRANS.getDBColNull(field));
        properties.put("isUnique", TRANS.getDBColUnique(field));
        properties.put("default", TRANS.getDBColDefault(field));
        properties.put("pk", TRANS.getDBColPK(field));
        // TODO AUTO_INCREMENT!
        // TODO handle FK!

        return properties;
    }

    public boolean isAnnotated(Object o, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(o) && o.getClass().isAnnotationPresent(annotationClass));
    }

    public boolean isAnnotated(Field field, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(field) && field.isAnnotationPresent(annotationClass));
    }

    public boolean isAnnotated(Class<?> cl, Class<? extends Annotation> annotationClass) {
        return (!Objects.isNull(cl) && cl.isAnnotationPresent(annotationClass));
    }



}
