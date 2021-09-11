package complements.database;

import application.helpers.Transformer;
import complements.database.annotations.Skip;
import complements.database.annotations.Table;
import complements.database.services.AnnotationService;
import complements.logger.LogHandler;

import java.lang.reflect.Field;
import java.util.*;

public class DBCreator {

    private static final LogHandler LOG = new LogHandler(DBCreator.class, "test_logs.txt");

    private static final Transformer TRANSFORMER = Transformer.getInstance();

    public void run() {
        List<Class<?>> classes = new ClassFinder(Table.class).loadAnnotatedClasses();

        AnnotationService service = new AnnotationService();
        Map<String, List<String>> allTables = service.getAllTables(classes);

        for (String key : allTables.keySet()) {
            System.out.println(key);
            for (String table : allTables.get(key)) {
                System.out.println("\t" + table);
            }
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
