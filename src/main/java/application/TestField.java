package application;

import complements.DBCreator;
import complements.annotations.AnnotationController;
import complements.annotations.Table;
import complements.database.ColumnName;
import complements.database.QueryBuilder;
import complements.database.TableName;
import application.helpers.Transformer;
import complements.PropertiesHandler;
import complements.logger.LogHandler;
import application.models.Rarity;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestField {

    private static final LogHandler LOG = new LogHandler(TestField.class, "test_log_file.txt");

    public static void main(String[] args) {

        // TODO replace...
        // TODO handle better...
        // TODO now it finds annotated classes only in root package, but not in subpackages... FIX!!!

        new DBCreator().run();

    }

    // TODO should remove, not good... lol
    /*private static void annotatedClassFinder() {
        String modelsPackage = PropertiesHandler.getInstance().getProperty("models-directory");
        Class<? extends Annotation> annotationClass = Table.class;
        URL root = getRoot(modelsPackage);

        File[] files = getFiles(root);

        if (files != null) {
            List<Class<?>> classes = getAnnotatedClasses(files, modelsPackage, annotationClass);
            if (classes.size() > 0) {
                AnnotationController controller = new AnnotationController();
                for (Class<?> cl : classes) {
                    System.out.println(controller.getTableStatement(cl));
                }
            }
        }
    }*/

    private static URL getRoot(String packageName) {
        ClassLoader cll = ClassLoader.getSystemClassLoader();
        Package p = cll.getDefinedPackage(packageName);

        return Thread.currentThread().getContextClassLoader().getResource(p.getName().replace(".", "/"));
    }

    private static File[] getFiles(URL root) {
        return new File(root.getFile()).listFiles((dir, name) -> name.endsWith(".class"));
    }

    // TODO rootPackage and annotationClass should be in app.properties...
    private static List<Class<?>> getAnnotatedClasses(File[] files, String rootPackage, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classes = new ArrayList<>();

        for (File file : files) {
            String className = file.getName().replaceAll(".class$", "");
            try {
                Class<?> cl = Class.forName(rootPackage + "." + className);
                if (cl.isAnnotationPresent(annotationClass)) {
                    classes.add(cl);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();        // TODO log
            }
        }

        return classes;
    }

}
