package application;

import application.annotations.AnnotationController;
import application.annotations.TestTable;
import application.database.Column;
import application.database.QueryBuilder;
import application.database.Table;
import application.helpers.EnumHelper;
import application.models.Rarity;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestField {

    public static void main(String[] args) {

        AnnotationController controller = new AnnotationController();

        String testTable = controller.getTableStatement(new TestTable());

        System.out.println(testTable);

    }

    private static void testEnumNameHandling() {
        String query1 = new QueryBuilder().select(Table.DRAGON, Column.ID, Column.UNIQUE_NAME, Column.DRAGON_TEXT)
                .where(Column.ID, false)
                .build();
        String query2 = new QueryBuilder().insert(Table.DRAGON, Column.UNIQUE_NAME, Column.DRAGON_TEXT, Column.DESIGN)
                .build();

        String veryRare = EnumHelper.getDBName(Rarity.VERY_RARE, true);
        String heroic = EnumHelper.getDBName(Rarity.HEROIC, true);

        System.out.println(query1);
        System.out.println(query2);
        System.out.println(veryRare);
        System.out.println(heroic);
    }

    private static void annotatedClassFinder() {
        AnnotationController controller = new AnnotationController();

        String e = controller.getTableStatement(new TestTable());
        System.out.println(e);

        try {
            ClassLoader cll = ClassLoader.getSystemClassLoader();
            Package p = cll.getDefinedPackage("application.annotations");
            URL root = Thread.currentThread().getContextClassLoader().getResource(p.getName().replace(".", "/"));

            //System.out.println(root);

            File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".class");
                }
            });

            List<Class<?>> classes = new ArrayList<>();

// Find classes implementing ICommand.
            for (File file : files) {
                String className = file.getName().replaceAll(".class$", "");
                Class<?> cl = Class.forName(p.getName() + "." + className);
                if (cl.isAnnotationPresent(application.annotations.Table.class)) {
                    classes.add(cl);
                }
            }

            for (Class<?> cls : classes) {
                System.out.println(cls.getSimpleName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
