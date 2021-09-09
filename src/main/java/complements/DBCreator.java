package complements;

import complements.annotations.Table;
import complements.logger.LogHandler;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class DBCreator {

    private static final LogHandler LOG = new LogHandler(DBCreator.class, "application_logs.txt");
    private static final Class<? extends Annotation> annotation = Table.class;

    public void run() {
        String root = PropertiesHandler.getInstance().getProperty("models-directory");
        List<Class<?>> classes = collectAgain(new File(root), new ArrayList<>());

        for (Class<?> cl : classes) {
            System.out.println(cl.getName());
        }
    }

    private List<Class<?>> collectAgain(File dirPath, List<Class<?>> classes) {
        File[] filesList = dirPath.listFiles();
        if (filesList != null) {
            for(File file : filesList) {
                if(file.isFile() && file.toString().contains(".java")) {
                    createClassAndAdd(file.toString(), classes);
                } else {
                    collectAgain(file, classes);
                }
            }
        }
        return classes;
    }

    private void createClassAndAdd(String className, List<Class<?>> classes) {
        try {
            Class<?> cl = Class.forName(transformPath(className));
            if (cl.isAnnotationPresent(annotation)) {
                classes.add(cl);
            }
        } catch (ClassNotFoundException e) {
            LOG.error("collectAgain(File dirPath, List<Class<?>> classes)", e.getMessage());
        }
    }

    // TODO move to other class?
    public String transformPath(String path) {
        int dot = path.lastIndexOf('.');
        int root = path.lastIndexOf("java/") + 5;
        return path.substring(root, dot).replace(File.separatorChar, '.');
    }

}
