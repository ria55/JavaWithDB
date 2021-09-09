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

    public Map<String, List<Properties>> getAllTables(List<Class<?>> classes) {
        Map<String, List<Properties>> allTables = new HashMap<>();

        for (Class<?> cl : classes) {
            String tableName = TRANS.getDBName(cl);
            List<Properties> props = getTable(cl);
            allTables.put(tableName, props);
        }

        return allTables;
    }

    private List<Properties> getTable(Class<?> cl) {
        List<Properties> tableCols = new ArrayList<>();

        for (Field field : cl.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Skip.class)) {
                tableCols.add(getColProps(field));
            }
        }

        return tableCols;
    }

    private Properties getColProps(Field field) {


        /* TODO write again: convert the properties to String, and do not create Properties!
        StringBuilder column = new StringBuilder();


        int length = TRANS.getDBColLength(field, type);
        String isNotNull = TRANS.getDBColNull(field);
        String isUnique = TRANS.getDBColUnique(field);
        String defaultValue = TRANS.getDBColDefault(field);
        String pk = TRANS.getDBColPK(field);

        column.append(TRANS.getDBName(field))
                .append(type.toString())
                .append(length > 0 ? "(" + length + ")" : "")
                .append(" ")
                .append(isNotNull.isBlank() ? "" : isNotNull + " ")
                .append(isUnique.isBlank() ? "" : isUnique + " ")
                .append(pk.isBlank() ? "" : pk + " ")
                .append(defaultValue.isBlank() ? "" : "DEFAULT " + "'" + defaultValue + "'")
                .append(",");*/


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
