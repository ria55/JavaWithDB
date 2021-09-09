package complements;

import application.helpers.Transformer;
import complements.annotations.Skip;
import complements.annotations.Table;
import complements.database.ClassFinder;
import complements.logger.LogHandler;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DBCreator {

    private static final LogHandler LOG = new LogHandler(DBCreator.class, "application_logs.txt");

    private static final Transformer TRANSFORMER = Transformer.getInstance();

    public void run() {
        List<Class<?>> classes = new ClassFinder(Table.class).loadAnnotatedClasses();

        for (Class<?> cl : classes) {
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

            LOG.test("run()", b.toString());
        }
    }

}
