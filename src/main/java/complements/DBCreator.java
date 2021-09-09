package complements;

import application.helpers.Transformer;
import complements.annotations.Skip;
import complements.annotations.Table;
import complements.database.ClassFinder;
import complements.database.QueryBuilder;
import complements.logger.LogHandler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class DBCreator {

    private static final LogHandler LOG = new LogHandler(DBCreator.class, "test_logs.txt");

    private static final Transformer TRANSFORMER = Transformer.getInstance();

    public void run() {
        List<Class<?>> classes = new ClassFinder(Table.class).loadAnnotatedClasses();

        for (Class<?> cl : classes) {
            Field[] fields = Arrays.stream(cl.getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(Skip.class))
                    .toArray(Field[]::new);
            String query = new QueryBuilder().createTable(cl, fields).build();

            LOG.test("run()", query);
        }
    }

    private String createTableStatement(Class<?> cl) {
        StringBuilder b = new StringBuilder();
        String tableName = TRANSFORMER.getDBName(cl);
        b.append("Table name: ").append(tableName).append("; ");
        b.append("field names: ");
        for (Field field : cl.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Skip.class)) {
                String fieldName = TRANSFORMER.getDBName(field);
                b.append(fieldName).append(", ");
            }
        }
        b.setLength(b.length() - 2);
        return b.toString();
    }

}
