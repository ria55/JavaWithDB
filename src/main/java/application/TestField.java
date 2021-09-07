package application;

import application.annotations.AnnotationController;
import application.annotations.Table;
import application.database.ColumnName;
import application.database.QueryBuilder;
import application.database.TableName;
import application.helpers.EnumHelper;
import application.models.Rarity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestField {

    public static void main(String[] args) {

        // TODO replace...
        // TODO handle better...
        annotatedClassFinder();

    }

    private static void testEnumNameHandling() {
        String query1 = new QueryBuilder().select(TableName.DRAGON, ColumnName.ID, ColumnName.UNIQUE_NAME, ColumnName.DRAGON_TEXT)
                .where(ColumnName.ID, false)
                .build();
        String query2 = new QueryBuilder().insert(TableName.DRAGON, ColumnName.UNIQUE_NAME, ColumnName.DRAGON_TEXT, ColumnName.DESIGN)
                .build();

        String veryRare = EnumHelper.getDBName(Rarity.VERY_RARE, true);
        String heroic = EnumHelper.getDBName(Rarity.HEROIC, true);

        System.out.println(query1);
        System.out.println(query2);
        System.out.println(veryRare);
        System.out.println(heroic);
    }

    private static void annotatedClassFinder() {
        String modelsPackage = getRootPackageName();
        Class<? extends Annotation> annotationClass = Table.class;      // TODO app.properties
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
    }

    private static String getRootPackageName() {
        try {
            Properties properties = new Properties();
            properties.loadFromXML(new FileInputStream("props.xml"));
            return properties.getProperty("models-package");
        } catch (IOException e) {
            e.printStackTrace();        // TODO log
        }

        return "";
    }

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
