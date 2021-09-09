package complements;

import application.helpers.Transformer;
import complements.annotations.Skip;
import complements.annotations.Table;
import complements.logger.LogHandler;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DBCreator {

    private static final LogHandler LOG = new LogHandler(DBCreator.class, "application_logs.txt");
    private static final Class<? extends Annotation> ANNOTATION = Table.class;

    private static final Transformer TRANSFORMER = Transformer.getInstance();

    public void run() {
        String root = PropertiesHandler.getInstance().getProperty("models-directory");
        List<Class<?>> classes = findAnnotatedClasses(new File(root), new ArrayList<>());

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

            LOG.info("run()", b.toString());
        }
    }

    private List<Class<?>> findAnnotatedClasses(File dirPath, List<Class<?>> classes) {
        File[] filesList = dirPath.listFiles();
        if (filesList != null) {
            for(File file : filesList) {
                if(file.isFile() && file.toString().contains(".java")) {
                    createClassAndAdd(file.toString(), classes);
                } else {
                    findAnnotatedClasses(file, classes);
                }
            }
        }
        return classes;
    }

    private void createClassAndAdd(String className, List<Class<?>> classes) {
        try {
            Class<?> cl = Class.forName(TRANSFORMER.getClassName(className));
            if (cl.isAnnotationPresent(ANNOTATION)) {
                classes.add(cl);
            }
        } catch (ClassNotFoundException e) {
            LOG.error("collectAgain(File dirPath, List<Class<?>> classes)", e.getMessage());
        }
    }

}
