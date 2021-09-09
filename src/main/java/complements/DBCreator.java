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

    // TODO delete this if only findAnnotatedClasses() use it
    private final PropertiesHandler PROPS = PropertiesHandler.getInstance();

    public void run() {
        String root = PROPS.getProperty("models-directory");
        List<Class<?>> classes = findAnnotatedClasses(new File(root), new ArrayList<>());

        for (Class<?> cl : classes) {
            StringBuilder b = new StringBuilder();
            String tableName = Transformer.getDBName(cl);
            b.append("Table name: ").append(tableName).append("; ");
            b.append("field names: ");

            for (int i = 0; i < cl.getDeclaredFields().length; i++) {
                String fieldName = Transformer.getDBName(cl.getDeclaredFields()[i]);
                b.append(fieldName);
                if (i < cl.getDeclaredFields().length - 1) {
                    b.append(", ");
                }
            }
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
            Class<?> cl = Class.forName(transformPath(className));
            if (cl.isAnnotationPresent(ANNOTATION)) {
                classes.add(cl);
            }
        } catch (ClassNotFoundException e) {
            LOG.error("collectAgain(File dirPath, List<Class<?>> classes)", e.getMessage());
        }
    }

    // TODO move to other class?
    public String transformPath(String path) {
        String rootDir = PROPS.getProperty("project-source-dir") + File.separatorChar;
        int dot = path.lastIndexOf('.');
        int root = path.lastIndexOf(rootDir) + rootDir.length();
        return path.substring(root, dot).replace(File.separatorChar, '.');
    }

}
