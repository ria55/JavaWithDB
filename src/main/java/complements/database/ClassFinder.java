package complements.database;

import application.helpers.Transformer;
import complements.PropertiesHandler;
import complements.annotations.Table;
import complements.logger.LogHandler;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ClassFinder {

    private static final LogHandler LOG = new LogHandler(ClassFinder.class, "application_logs.txt");

    private final Class<? extends Annotation> ANNOTATION;
    private final Transformer TRANSFORMER = Transformer.getInstance();

    public ClassFinder(Class<? extends Annotation> annotation) {
        ANNOTATION = annotation;
    }

    public List<Class<?>> loadAnnotatedClasses() {
        String root = PropertiesHandler.getInstance().getProperty("models-directory");
        return findAnnotatedClasses(new File(root), new ArrayList<>());
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
