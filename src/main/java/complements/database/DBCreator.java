package complements.database;

import application.helpers.Transformer;
import complements.database.annotations.Skip;
import complements.database.annotations.Table;
import complements.logger.LogHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBCreator {

    private static final LogHandler LOG = new LogHandler(DBCreator.class, "test_logs.txt");

    private static final Transformer TRANSFORMER = Transformer.getInstance();

    public void run() {
        List<Class<?>> classes = new ClassFinder(Table.class).loadAnnotatedClasses();

        List<String> tableStatements = new ArrayList<>();

        for (Class<?> cl : classes) {
            tableStatements.add(createTableStatement(cl));
        }
    }

    private String createTableStatement(Class<?> cl) {
        Field[] fields = Arrays.stream(cl.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Skip.class))
                .toArray(Field[]::new);
        String query = new QueryBuilder().createTable(cl, fields).build();

        LOG.test("run()", query);

        return query;
    }

}
