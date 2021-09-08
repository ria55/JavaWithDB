package application.helpers;

import application.Main;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

    private static PropertiesHandler handler;

    private Properties properties;

    private PropertiesHandler() {}

    public static PropertiesHandler getInstance() {
        if (handler == null) {
            handler = new PropertiesHandler();
            handler.properties = handler.loadProperties();
        }
        return handler;
    }

    private Properties loadProperties() {
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream(System.getenv("PROP_FILE"));
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (Exception e) {
            e.printStackTrace();            // TODO log
            return null;
        }
    }

    public Properties getProperties() {
        return handler.properties;
    }

    public String getProperty(String propertyName) {
        return handler.properties.getProperty(propertyName);
    }

}
